import java.util.ArrayList;

public class MyTask implements ComsController{

    private ServerConnection serverConnection;
    private ChatViewer viewer;
    private ArrayList<Connection> connections;
    private ClientConnection clientConnection;

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public ChatViewer getViewer() {
        return viewer;
    }

    public void setViewer(ChatViewer viewer) {
        this.viewer = viewer;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
    public ArrayList<Connection> getFaultConnections(){
        ArrayList<Connection> faultConnections = new ArrayList<>();
        for (int i=0;i<this.connections.size();i++){
            if(connections.get(i).getSocket()==null && connections.get(i).getIp()!=null && connections.get(i).getPort()!=null){
                faultConnections.add(connections.get(i));
            }
        }
        return faultConnections;
    }
    public void processFrame(Frame frame){
        this.viewer.getLamina().getCampoChat().append("\n"+frame.getHeader().getNick()+": "+frame.getPayload().getMessage());
    }
    public boolean acceptInboundConnection(Connection connection){
        boolean validConnection = true;
        for(int i=0;i<connections.size();i++){
            if(!connections.get(i).isConnectionOK()){
                if(connections.get(i).getIp()==connection.getIp()){
                    validConnection = false;
                }
            }
        }
        return validConnection;
    }

    public MyTask(){
        this.viewer = new ChatViewer(this);
        this.serverConnection = new ServerConnection(this);
        this.clientConnection = new ClientConnection(this);

        this.connections = new ArrayList<>();
        Connection con1 = new Connection(viewer,this);
        con1.setIp("127.0.0.1");
        con1.setPort(1234);
        this.connections.add(con1);
        Connection con2 = new Connection(viewer,this);
        con2.setIp("127.0.0.1");
        con2.setPort(1234);
        this.connections.add(con2);

        Thread viewer = new Thread(this.viewer);
        viewer.start();

    }

    public void startConnection(){

        Thread tServer = new Thread(serverConnection);
        tServer.start();
        Thread tClient = new Thread(clientConnection);
        tClient.start();

        for(int i=0;i<connections.size();i++){
            Thread tConecction = new Thread(connections.get(i));
            tConecction.start();
        }

    }

    public static void main(String[]args){
        new MyTask();
    }


}
