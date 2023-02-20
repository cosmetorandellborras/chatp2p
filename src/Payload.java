import java.io.Serializable;

public class Payload implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Payload(String message){
        this.message = message;
    }
    public Payload(){

    }
}
