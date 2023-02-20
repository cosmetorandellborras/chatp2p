import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable{
    private String ip;
    private Integer port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ChatViewer viewer;
    private MyTask myTask;
    private int lastTimeRecievedMessage;
    private HCC healthCareController;

    public MyTask getMyTask() {
        return myTask;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public int getLastTimeRecievedMessage() {
        return lastTimeRecievedMessage;
    }

    public void setLastTimeRecievedMessage(int lastTimeRecievedMessage) {
        this.lastTimeRecievedMessage = lastTimeRecievedMessage;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public synchronized boolean isConnectionOK() {
        return this.socket!=null;
    }

    public synchronized Socket getSocket() {
        return this.socket;
    }

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
        this.setPort(socket.getPort());
        this.setIp(socket.getInetAddress().getHostAddress());
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("EXCEPCION SET SOCKET");
            throw new RuntimeException(e);
        }
        this.healthCareController.setStatus(HCCStatus.ok);
    }
    public Connection(ChatViewer viewer,MyTask myTask){
        this.myTask = myTask;
        this.viewer = viewer;
        this.healthCareController = new HCC(this);
        Thread hcc = new Thread(this.healthCareController);
        hcc.start();

    }
    public void escribir(String ip,String nick,String texto,TypeFrame type){
        try {
            Frame mensaje = new Frame(ip,nick,texto,type);
            if(mensaje.getHeader().getType() != TypeFrame.ping && mensaje.getHeader().getType() != TypeFrame.ack ){
                this.viewer.getLamina().getCampoChat().append("\n"+mensaje.getHeader().getNick()+": "+mensaje.getPayload().getMessage());
            }
            out.writeObject(mensaje);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void recibir(){
        System.out.println("CONECTION ESPERANDO MENSAJE");
        try {
            Frame mensajeRecibido = new Frame();
            mensajeRecibido = (Frame) in.readObject();
            if(mensajeRecibido.getHeader().getType()==TypeFrame.ping){
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
                this.escribir("","","",TypeFrame.ack);

            }else if(mensajeRecibido.getHeader().getType()==TypeFrame.ack){
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
            }
            else if(mensajeRecibido.getHeader().getNick().equals(this.viewer.getLamina().getNick().getText())){
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
            }
            else{
                this.myTask.processFrame(mensajeRecibido);
                ArrayList<Connection> connections = myTask.getConnections();
                for(int i=0;i< connections.size();i++){
                    if(connections.get(i).isConnectionOK()){
                        connections.get(i).escribir(mensajeRecibido.getHeader().getIpSource(),mensajeRecibido.getHeader().getNick(),mensajeRecibido.getPayload().getMessage(),TypeFrame.message);
                    }

                }
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
            }
        } catch (IOException e) {
            this.killSocket();
            //throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
        }
    }
    public void killSocket(){
        try {
            this.socket.close();
        } catch (IOException e) {

        }
        this.socket = null;
        //this.healthCareController.setStatus(HCCStatus.ok);
    }

    @Override
    public void run() {
        while(true){
            if(this.isConnectionOK()){
                    this.recibir();
                    //this.viewer.getLamina().getCampoChat().append(in.readLine());
                    System.out.println("Mensaje recibido");
                    this.viewer.getLamina().getStatusPanel1().setBackground(Color.GREEN);
                    ArrayList<Connection> con = myTask.getConnections();
                    for(int i=0;i<con.size();i++){
                        if(con.get(i).getSocket()!=null){
                            System.out.println("Conexion "+String.valueOf(i)+" funcional");
                        }
                    }
            }
            else{
                this.viewer.getLamina().getStatusPanel1().setBackground(Color.RED);
                //System.out.println("La conexion CONNECTION no es OK");
            }
        }
    }
}