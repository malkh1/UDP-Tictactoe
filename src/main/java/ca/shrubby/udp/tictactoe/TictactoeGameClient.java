package ca.shrubby.udp.tictactoe;

import java.io.IOException;

public class tttgc {

    public static void main(String[] args) {
        String ipAddress;
        if (args.length > 0) {
            ipAddress = args[0];
            
            try {
                Client.main(new String[]{ipAddress});
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println("You need to enter an ip address to play the game!\n"
                    + "Ex: \"java -jar tictactoe-client.jar 76.69.113.172\" to connect to a foreign host!\n"
                    + "Ex: \"java -jar tictactoe-client.jar 192.168.2.20\" to connect to a local host!");
        }

    }
}
