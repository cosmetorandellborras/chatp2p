import java.util.ArrayList;

public interface ComsController {

    public ArrayList<Connection> getFaultConnections();
    public void processFrame(Frame frame);
}
