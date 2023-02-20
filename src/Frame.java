import java.io.Serializable;

public class Frame implements Serializable {

    private Header header;
    private Payload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Frame(String ipSource, String nick, String mensaje,TypeFrame type){
        this.header = new Header();
        this.payload = new Payload();
        this.header.setIpSource(ipSource);
        this.header.setNick(nick);
        this.header.setType(type);
        this.payload.setMessage(mensaje);
    }
    public Frame(){
        this.header = new Header();
        this.payload = new Payload();
    }
}
