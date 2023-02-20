import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class LaminaMarco extends JPanel implements ActionListener{

    private ChatViewer viewer;

    private JLabel ip1Label;
    private JTextField ip1;
    private JLabel port1Label;
    private JTextField port1;
    private JLabel ip2Label;
    private JTextField ip2;
    private JLabel port2Label;
    private JTextField port2;
    private JLabel puertoEscuchaServidorLabel;
    private JTextField puertoEscuchaServidor;
    private JPanel status1panel;
    private JPanel status2panel;
    private JLabel nickLabel;
    private JTextField nick;
    private JButton connect;
    private JTextArea campoChat;
    private JTextField mensaje;
    private JButton botonEnviar;

    public JTextArea getCampoChat(){
        return campoChat;
    }
    public JTextField getNick(){
        return nick;
    }

    public LaminaMarco(ChatViewer viewer){
        this.viewer = viewer;
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        ip1Label = new JLabel("IP 1:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(ip1Label,gbc);

        ip1 = new JTextField(15);
        ip1.addActionListener(this);
        gbc.gridx = 1;
        add(ip1,gbc);

        port1Label = new JLabel("PORT 1:");
        gbc.gridx = 2;
        add(port1Label,gbc);

        port1 = new JTextField(4);
        port1.addActionListener(this);
        gbc.gridx = 3;
        add(port1,gbc);

        ip2Label = new JLabel("IP 2:");
        gbc.gridx = 5;
        add(ip2Label,gbc);

        ip2 = new JTextField(15);
        ip2.addActionListener(this);
        gbc.gridx = 6;
        add(ip2,gbc);

        port2Label = new JLabel("PORT 2:");
        gbc.gridx = 7;
        add(port2Label,gbc);

        port2 = new JTextField(4);
        port2.addActionListener(this);
        gbc.gridx = 8;
        add(port2,gbc);

        puertoEscuchaServidorLabel = new JLabel("PUERTO S: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(puertoEscuchaServidorLabel,gbc);

        puertoEscuchaServidor = new JTextField(4);
        puertoEscuchaServidor.addActionListener(this);
        gbc.gridx = 1;
        add(puertoEscuchaServidor,gbc);

        status1panel = new JPanel();
        gbc.gridx = 2;
        add(status1panel,gbc);

        status2panel = new JPanel();
        gbc.gridx = 8;
        add(status2panel,gbc);

        nickLabel = new JLabel("NICK:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nickLabel,gbc);

        nick = new JTextField(5);
        gbc.gridx = 1;
        add(nick,gbc);

        connect = new JButton("Connect");
        connect.addActionListener(this);
        gbc.gridx = 2;
        add(connect,gbc);

        campoChat = new JTextArea();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 9;
        gbc.gridheight = 6;
        gbc.weighty=1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        add(campoChat,gbc);

        mensaje = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = 1;
        gbc.gridwidth = 7;
        gbc.weighty=0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(mensaje,gbc);

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        gbc.gridx = 8;
        gbc.gridwidth = 1;
        add(botonEnviar,gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botonEnviar){
            ArrayList<Connection> connections = this.viewer.getMyTask().getConnections();
            for(int i=0;i<connections.size();i++){
                if(connections.get(i).isConnectionOK()){
                    connections.get(i).escribir(ip1.getText().toString(),nick.getText().toString(),mensaje.getText().toString(),TypeFrame.message);
                }
            }

            mensaje.setText("");
        }else if(e.getSource() == port1){
            //this.viewer.getMyTask().getClientConnection().setPort(Integer.parseInt(port1.getText()));
            System.out.println("Puerto cliente establecido "+port1.getText());
        }else if(e.getSource() == puertoEscuchaServidor){
            this.viewer.getMyTask().getServerConnection().setPort(Integer.parseInt(puertoEscuchaServidor.getText()));
            System.out.println("Puerto servidor establecido "+puertoEscuchaServidor.getText());
        }else if(e.getSource() == ip2){
            String ip = ip2.getText().trim();
            //this.viewer.getMyTask().getClientConnection().setIp(ip);
            System.out.println("IP destino "+ip);
        }else if (e.getSource() == connect){
            this.viewer.getMyTask().startConnection();
        }
    }

    public JPanel getStatusPanel1(){
        return status1panel;
    }
    public JPanel getStatusPanel2(){
        return status2panel;
    }
}
