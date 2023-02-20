import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection implements Runnable {

    private Integer port;
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

    public ServerConnection(MyTask myTask){
        this.myTask = myTask;
    }

    @Override
    public void run() {
        while(true){
            ArrayList<Connection> faultConnections = myTask.getFaultConnections();

            for(int i=0;i<faultConnections.size();i++){

                if(faultConnections.get(i).getSocket()==null && faultConnections.get(i).getIp()!=null && faultConnections.get(i).getPort()!=null){
                    ServerSocket serverSocket = null;
                    try {
                        serverSocket = new ServerSocket(faultConnections.get(i).getPort());
                    } catch (IOException e) {
                        //throw new RuntimeException(e);
                    }
                    try{
                        if(serverSocket != null){
                            Socket socket = serverSocket.accept();
                            if(!faultConnections.get(i).isConnectionOK()){
                                faultConnections.get(i).setSocket(socket);
                            }
                            else{
                                socket.close();
                            }
                        }
                    }catch (IOException e){

                    }

                }

            }
        }
    }
}