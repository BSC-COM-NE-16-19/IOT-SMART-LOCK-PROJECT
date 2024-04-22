package engineers.iot.smartlockapp.Model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket client;

    private DataOutputStream dataToServer;

    public Client() {

    }

    public void connectToServer(String url, int port) {

        try {
            this.client = new Socket(url,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDataOutputStream() {

        if(this.getClient().isConnected()) {
            try {
                this.dataToServer = new DataOutputStream(this.getClient().getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendCommand(String command) {

        try {
            if(this.getClient().isConnected()) {
                this.getDataToServer().writeUTF(command);
                this.getClient().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataOutputStream getDataToServer() {
        return dataToServer;
    }

    public Socket getClient() {
        return client;
    }


}
