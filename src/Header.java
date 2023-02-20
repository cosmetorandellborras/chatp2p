import java.io.Serializable;
import java.lang.reflect.Type;

public class Header implements Serializable {

    private String ipSource;
    private String nick;
    private TypeFrame type;

    public String getIpSource() {
        return ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public TypeFrame getType() {
        return type;
    }

    public void setType(TypeFrame type) {
        this.type = type;
    }

    public Header(String ip, String nick, TypeFrame type){
        this.ipSource = ip;
        this.nick = nick;
        this.type = type;
    }
    public Header(){

    }
}
