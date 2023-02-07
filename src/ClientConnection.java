import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable{

    private String ip;
    private Integer port;
    private Connection con;
    private MyTask myTask;

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

    public void setPort(Integer port) {
        this.port = port;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ClientConnection(Connection con,MyTask myTask){
        this.myTask = myTask;
        this.con = con;
    }

    @Override
    public void run() {
        while(true){
            if(!con.isConnectionOK()){
                try {
                    //System.out.println("1");
                    //System.out.println("prueba conexion puerto "+String.valueOf(this.port)+" ip: "+this.ip);
                    Socket socket = new Socket(this.ip,this.port);
                    System.out.println("CLIENT CONNECTIO Antes de hacer set socket");
                    con.setSocket(socket);
                    //con.escribir("Soy yo");
                    System.out.println("CLIENT CONNECTIO Despues de hacer set socket,"+con.getSocket().getPort()+" "+con.getSocket().getInetAddress().getHostAddress());
                    //System.out.println("3");
                    //JOptionPane.showMessageDialog(null,"Conexio acceptada");
                } catch (IOException e) {
                    System.out.println("Error al conectar client connector");
                    //throw new RuntimeException(e);
                    }

                }
            }
        }
}
