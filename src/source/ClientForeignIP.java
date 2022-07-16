package source;

import java.io.IOException;

public class ClientForeignIP {
    public static void main(String[] args) {
        String ipAddress;
        if(args.length > 0){
            ipAddress = args[0];
        }
        else{
            ipAddress = "xx.xx.xx.xx";//put your ip address here
        }
        try {
            Client.main(new String[]{ipAddress});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
