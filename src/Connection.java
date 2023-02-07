import java.awt.*;
import java.io.*;
import java.net.Socket;

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
    public void escribir(String nick,String texto){
        try {
            Mensaje mensaje = new Mensaje(nick,texto);
            if(!mensaje.getNick().equals("ping") && !mensaje.getNick().equals("ping2") ){
                this.viewer.getLamina().getCampoChat().append("\n"+mensaje.getNick()+": "+mensaje.getMensaje());
            }
            out.writeObject(mensaje);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void recibir(){
        System.out.println("CONECTION ESPERANDO MENSAJE");
        try {
            Mensaje mensajeRecibido = new Mensaje();
            mensajeRecibido = (Mensaje) in.readObject();
            if(mensajeRecibido.getNick().equals("ping")){
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
                this.escribir("ping2","");

            }else if(mensajeRecibido.getNick().equals("ping2")){
                this.lastTimeRecievedMessage = (int) System.currentTimeMillis();
            }
            else{
                this.viewer.getLamina().getCampoChat().append("\n"+mensajeRecibido.getNick()+": "+mensajeRecibido.getMensaje());
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
    public void sendPing(){
        this.escribir("root","");
    }

    @Override
    public void run() {
        while(true){
            if(this.isConnectionOK()){
                    this.recibir();
                    //this.viewer.getLamina().getCampoChat().append(in.readLine());
                    System.out.println("Mensaje recibido");
                    this.viewer.getLamina().getStatusPanel().setBackground(Color.GREEN);
            }
            else{
                this.viewer.getLamina().getStatusPanel().setBackground(Color.RED);
                //System.out.println("La conexion CONNECTION no es OK");
            }
        }
    }
}