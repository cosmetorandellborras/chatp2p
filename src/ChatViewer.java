import javax.swing.*;

public class ChatViewer extends JFrame {
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
        setBounds(600,300,280,400);
        lamina = new LaminaMarco(this);
        add(lamina);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}