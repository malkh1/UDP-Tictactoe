package ca.shrubby.udp.tictactoe;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        boolean firstRun = true;
        var socket = new DatagramSocket();
        socket.setSoTimeout(120000); // timeout after 2 mins of inactivity
        byte[] buffer = new byte[1024];
        InetAddress serverAddress;
        if(args.length > 0){
            serverAddress = InetAddress.getByName(args[0]);
        }
        else{
            serverAddress = InetAddress.getByName("localhost");
        }
        var serverPort = 4545;
        JFrame clientWindow = new JFrame("Tictactoe Client");
        JPanel bodyPanel = new JPanel();
        JTextArea messageBox = new JTextArea(10,30);
        JScrollPane scrollPane = new JScrollPane(messageBox);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bodyPanel.add(scrollPane);
        clientWindow.add(bodyPanel);
        clientWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clientWindow.pack();
        clientWindow.setVisible(true);
        //Handshake
        buffer[0] = 1;
        var packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
        messageBox.append("Connecting to server @" + serverAddress + ":" + serverPort + "...\n");
        socket.send(packet);
        socket.receive(packet);
        messageBox.append("Server is online. Waiting to see if player(you) goes first\n");

        //determining if player goes first
        boolean firstTurn = buffer[0] == 0;
        if(firstTurn){
            messageBox.append("You have the first turn\n");
        }
        else {
            messageBox.append("You are not going first\n");
        }
        boolean WHOEVER_WENT_FIRST_IN_THE_BEGINNING_OF_THE_GAME_FFS = firstTurn;
        messageBox.append("Waiting to receive new port number from server thread...\n");
        //receiving new port from the ServerThread
        socket.receive(packet);
        serverPort = packet.getPort();
        //interaction
        buffer = new byte[1024];
        TicTacToeController controller = null;
        messageBox.append("Waiting for server to begin game...\n");
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        messageBox.append("Game started!\n");
        clientWindow.dispose();
        while(true){
            var byteStream = new ByteArrayInputStream(buffer);
            var deserializeStream = new ObjectInputStream(byteStream);
            TicTacToe ticTacToe = (TicTacToe) deserializeStream.readObject();

            if(firstRun){
                controller = new TicTacToeController(ticTacToe);
                var view = new TicTacToeView(controller);
                controller.setView(view);
                firstRun = false;
            }
            if(firstTurn){
                controller.setTurn(WHOEVER_WENT_FIRST_IN_THE_BEGINNING_OF_THE_GAME_FFS);
            }
            else {
                controller.setTurn(true);
            }
            controller.updateGame();
            if(firstTurn){
                controller.setModel(ticTacToe);
                controller.updateGame();
                controller.checkWin();
                if (TicTacToeController.gameOver) {
                    System.exit(0);
                }
                controller.enableGame();
                controller.waitForClick();
                var byteOutStream = new ByteArrayOutputStream();
                var serializeStream = new ObjectOutputStream(byteOutStream);
                serializeStream.writeObject(ticTacToe);
                buffer = byteOutStream.toByteArray();
                packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);

                serializeStream.flush();
                byteOutStream.flush();
                serializeStream.close();
                byteOutStream.close();

                controller.checkWin();
                if(TicTacToeController.gameOver){
                    System.exit(0);
                }
            }
            controller.disableGame();
            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            firstTurn = true;

        }
    }
}
