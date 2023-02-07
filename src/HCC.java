public class HCC implements Runnable{

    private static final int healthTimeOut = 1000;
    private HCCStatus status;
    private Connection connection;

    public int getHealthTimeOut() {
        return healthTimeOut;
    }

    public HCCStatus getStatus() {
        return status;
    }

    public void setStatus(HCCStatus status) {
        this.status = status;
    }
    public HCC(Connection con){
        this.connection = con;
    }

    @Override
    public void run() {
        while(true){
            if(connection.isConnectionOK()){
                int lapseTime = (int) (System.currentTimeMillis() - connection.getLastTimeRecievedMessage());
                if(lapseTime > healthTimeOut){
                    if(this.status == HCCStatus.ok){
                        this.connection.escribir("ping","");
                        this.status = HCCStatus.waitingAsk;
                    }
                    else{
                        this.connection.killSocket();
                        this.status = HCCStatus.ok;
                    }
                }
                else{
                    this.status = HCCStatus.ok;
                }
            }
            try {
                Thread.sleep(healthTimeOut/5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
