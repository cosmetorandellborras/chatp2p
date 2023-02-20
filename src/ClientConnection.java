import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection implements Runnable{

    private MyTask myTask;

    public MyTask getMyTask() {
        return myTask;
    }

    public ClientConnection(MyTask myTask){
        this.myTask = myTask;
    }

    @Override
    public void run() {
        while(true){
            ArrayList<Connection> faultConnections = myTask.getFaultConnections();
            for(int i =0;i<faultConnections.size();i++){

                if(faultConnections.get(i).getSocket()==null && faultConnections.get(i).getIp()!=null && faultConnections.get(i).getPort()!=null){
                    try {

                        Socket socket = new Socket(faultConnections.get(i).getIp(),faultConnections.get(i).getPort());
                        System.out.println("CLIENT CONNECTIO Antes de hacer set socket");
                        faultConnections.get(i).setSocket(socket);

                        System.out.println("CLIENT CONNECTIO Despues de hacer set socket,"+faultConnections.get(i).getSocket().getPort()+" "+faultConnections.get(i).getSocket().getInetAddress().getHostAddress());

                    } catch (IOException e) {
                        System.out.println("Error al conectar client connector");
                    }
                }
            }
        }
    }
}
