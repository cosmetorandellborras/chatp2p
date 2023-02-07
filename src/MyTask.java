public class MyTask {

    private ServerConnection serverConnection;
    private ChatViewer viewer;
    private Connection connection;
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public MyTask(){
        this.viewer = new ChatViewer(this);
        this.connection = new Connection(viewer,this);
        this.serverConnection = new ServerConnection(connection,this);
        this.clientConnection = new ClientConnection(connection,this);
    }

    public void startConnection(){
        Thread tConecction = new Thread(connection);
        tConecction.start();
        Thread tServer = new Thread(serverConnection);
        tServer.start();
        Thread tClient = new Thread(clientConnection);
        tClient.start();
    }

    public static void main(String[]args){
        new MyTask();
    }


}
