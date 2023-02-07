import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Integer port;
    private Connection con;
    private MyTask myTask;

    public MyTask getMyTask() {
        return myTask;
    }

    public Integer getPort() {
        return port;
    }

    public synchronized void setPort(Integer port) {
        this.port = port;
    }

    public ServerConnection(Connection con,MyTask myTask){
        this.myTask = myTask;
        this.con = con;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            if(this.port!=null){
                serverSocket = new ServerSocket(port);
            }else{
                System.err.println("Server con puerto null");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true){
            try {
                if(!con.isConnectionOK() && serverSocket!=null) {
                    System.out.println("**********"+con.getSocket());
                    System.out.println("Waiting..."+String.valueOf(port));
                    Socket socket = serverSocket.accept();
                    //System.out.println("Conexio acceptada "+socket.getPort()+" "+socket.getInetAddress().getHostAddress());
                    if(!con.isConnectionOK()){
                        System.out.println("Conexio acceptada "+socket.getPort()+" "+socket.getInetAddress().getHostAddress());
                        con.setSocket(socket);
                        //con.recibir();
                        //JOptionPane.showMessageDialog(null,"Conexio acceptada "+socket.getPort()+" "+socket.getInetAddress().getHostAddress());
                    }
                    else{
                        System.out.println("Conexio rebutjada "+socket.getPort()+" "+socket.getInetAddress().getHostAddress());
                        socket.close();
                    }

                }

            } catch (IOException e) {
                System.out.println(String.valueOf(port));
                throw new RuntimeException(e);
            }

        }
    }
}