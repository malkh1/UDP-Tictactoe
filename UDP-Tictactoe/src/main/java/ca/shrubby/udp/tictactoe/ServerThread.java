package ca.shrubby.udp.tictactoe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


import static ca.shrubby.udp.tictactoe.Server.NUM_PLAYERS;

public class ServerThread implements Runnable{
    private DatagramSocket socket;
    private ObjectOutputStream serializeStream;
    private ByteArrayOutputStream byteStream;
    private int[] clientPort;
    private InetAddress[] clientAddress;

    public ServerThread(int[] clientPort, InetAddress[] clientAddress){
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(120000); // 2 min of inactivity will time out thread
            byteStream = new ByteArrayOutputStream();
            serializeStream = new ObjectOutputStream(byteStream);
            this.clientPort = clientPort;
            this.clientAddress = clientAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try{
            TicTacToe ticTacToe = new TicTacToe();
            serializeStream.writeObject(ticTacToe);
            byte[] buffer = byteStream.toByteArray();

            //sending clients the new port
            for (int i = 0; i < NUM_PLAYERS; i++){
                socket.send(new DatagramPacket(new byte[]{1}, 1, clientAddress[i], clientPort[i]));
                System.out.println("sending to " + clientAddress[i]);
            }


            //sending the serialized tictactoe object
            for (int i = 0; i < NUM_PLAYERS; i++)
                socket.send(new DatagramPacket(buffer, buffer.length, clientAddress[i], clientPort[i]));
            //game loop
            while(true){
                buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);
                if(receivePacket.getPort() == clientPort[0]){
                    socket.send(new DatagramPacket(buffer, buffer.length, clientAddress[1], clientPort[1]));
                }
                else {
                    socket.send(new DatagramPacket(buffer, buffer.length, clientAddress[0], clientPort[0]));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
