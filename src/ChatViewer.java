import javax.swing.*;
import java.awt.*;

public class ChatViewer extends JFrame implements Runnable {
    private MyTask myTask;

    private LaminaMarco lamina;

    public LaminaMarco getLamina() {
        return lamina;
    }

    public void setLamina(LaminaMarco lamina) {
        this.lamina = lamina;
    }

    public MyTask getMyTask() {
        return myTask;
    }

    public ChatViewer(MyTask myTask){
        this.myTask = myTask;
        setBounds(600,300,1000,800);
        lamina = new LaminaMarco(this);
        add(lamina);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        while(true){
            if(this.myTask.getConnections().get(0).getSocket()!=null){
                this.lamina.getStatusPanel1().setBackground(Color.GREEN);
            }else{
                this.lamina.getStatusPanel1().setBackground(Color.RED);
            }
            if(this.myTask.getConnections().get(1).getSocket()!=null){
                this.lamina.getStatusPanel2().setBackground(Color.GREEN);
            }else{
                this.lamina.getStatusPanel2().setBackground(Color.RED);
            }
        }
    }
}