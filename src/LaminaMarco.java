import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LaminaMarco extends JPanel implements ActionListener{

    private ChatViewer viewer;
    private JTextField mensaje;
    private JButton botonEnviar;
    private JTextArea campoChat;
    private JTextField nick;
    private JTextField portClient;
    private JTextField portServer;
    private JTextField ipDestino;
    private JButton connectButton;
    private JPanel statusPanel;

    public JTextArea getCampoChat(){
        return campoChat;
    }

    public LaminaMarco(ChatViewer viewer){
        this.viewer = viewer;
        nick = new JTextField(5);
        add(nick);

        JLabel texto = new JLabel("-CHAT-");
        add(texto);

        portClient = new JTextField(4);
        portClient.addActionListener(this);
        add(portClient);

        portServer = new JTextField(4);
        portServer.addActionListener(this);
        add(portServer);

        JLabel ipDestinoLabel = new JLabel("-IP-");
        add(ipDestinoLabel);

        ipDestino = new JTextField(15);
        ipDestino.addActionListener(this);
        add(ipDestino);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        add(connectButton);

        statusPanel = new JPanel();
        statusPanel.setBackground(Color.RED);
        add(statusPanel);

        campoChat = new JTextArea(12,20);
        add(campoChat);
        mensaje = new JTextField(20);
        add(mensaje);

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        add(botonEnviar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botonEnviar){
            this.viewer.getMyTask().getConnection().escribir(nick.getText().toString(),mensaje.getText().toString());
            mensaje.setText("");
        }else if(e.getSource() == portClient){
            this.viewer.getMyTask().getClientConnection().setPort(Integer.parseInt(portClient.getText()));
            System.out.println("Puerto cliente establecido "+portClient.getText());
        }else if(e.getSource() == portServer){
            this.viewer.getMyTask().getServerConnection().setPort(Integer.parseInt(portServer.getText()));
            System.out.println("Puerto servidor establecido "+portServer.getText());
        }else if(e.getSource() == ipDestino){
            String ip = ipDestino.getText().trim();
            this.viewer.getMyTask().getClientConnection().setIp(ip);
            System.out.println("IP destino "+ip);
        }else if (e.getSource() == connectButton){
            this.viewer.getMyTask().startConnection();
        }
    }

    public JPanel getStatusPanel(){
        return statusPanel;
    }
}
