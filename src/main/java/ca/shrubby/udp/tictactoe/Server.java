package ca.shrubby.udp.tictactoe;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/*
USE OBJECT SERIALIZATION TO SERIALIZE THE TICTACTOE OBJECT
SEND THE SERIALIZED OBJECTS USING UDP PACKETS
USE BYTEARRAY STREAMS FOR HOLDING SERIALIZED OBJECTS
TO IMPLEMENT MULTIPLE GAMES, EACH GAME WILL HAVE A GAME ID.
EACH GAME THREAD WILL HAVE ITS OWN GAME ID AND THE CLIENTS WILL USE THAT NUMBER TO COMMUNICATE WITH THE CORRECT
GAME THREAD
 */
public class Server {
    public static final int NUM_PLAYERS = 2;
    public static void main(String[] args) throws IOException {
        var socket = new DatagramSocket(4545);
        byte[] buffer = new byte[1024];
        var receivePacket = new DatagramPacket(buffer, buffer.length);

        InetAddress[] clientAddress = new InetAddress[NUM_PLAYERS];
        int[] clientPort = new int[NUM_PLAYERS];
        int[] turnOrder = new int[NUM_PLAYERS];
        //int[] testPort = new int[]{6565,6565};
        //deciding who goes first
        turnOrder[0] = new Random().nextInt(NUM_PLAYERS);
        turnOrder[1] = (turnOrder[0] == 0) ? 1 : 0;

        JFrame serverWindow = new JFrame("UDP Tictactoe Server");
        JPanel bodyPanel = new JPanel();
        JTextArea messageBox = new JTextArea(25,25);
        JScrollPane scrollPane = new JScrollPane(messageBox);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bodyPanel.add(scrollPane);
        serverWindow.add(bodyPanel);
        serverWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverWindow.pack();
        serverWindow.setVisible(true);

        while(true){
            messageBox.append("Looking for new players to connect...\n\n");
            for (int i = 0; i < NUM_PLAYERS; i++) {
                messageBox.append("waiting for client " + (i + 1) + " to connect...\n");
                socket.receive(receivePacket);
                clientAddress[i] = receivePacket.getAddress();
                clientPort[i] = receivePacket.getPort();
                messageBox.append("Client connected from "+ clientAddress[i] + ":" + clientPort[i] + "\n");
                buffer[0] = (byte) turnOrder[i];
                var sendPacket = new DatagramPacket(buffer, buffer.length,
                        clientAddress[i], clientPort[i]);
//                var sendPacket = new DatagramPacket(buffer, buffer.length,
//                        clientAddress[i], testPort[i]);
                messageBox.append("sending acknowledgement back to client\n\n");
                socket.send(sendPacket);
            }
            messageBox.append("Two clients connected. Game starting NOW\n\n");
            new Thread(new ServerThread(clientPort, clientAddress)).start();
            //new Thread(new ServerThread(testPort, clientAddress)).start();
        }

    }
}
