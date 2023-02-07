import java.io.Serializable;

public class Mensaje implements Serializable {

    private String nick;
    private String mensaje;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(String nick, String mensaje){
        this.nick = nick;
        this.mensaje = mensaje;
    }
    public Mensaje(){

    }
}
