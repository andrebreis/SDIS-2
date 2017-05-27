package logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by chrx on 5/26/17.
 */
public class CommunicationThread extends Thread{

    private GameLogic logic;
    private DatagramSocket socket;

    public CommunicationThread(GameLogic logic, DatagramSocket socket) {
        this.logic = logic;
        this.socket = socket;
    }

    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // figure out response
                String cmd = new String(packet.getData(), 0, packet.getLength());
                System.out.println(cmd);
                buf = processCommand(cmd).getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        socket.close();
    }

    private String  processCommand(String cmd){
        String[] cmdSplit= cmd.split(" ");
        if(cmdSplit[0].equals(MessageType.CZARCHANGE.name())){
            if(cmdSplit.length == 2){
                logic.changeCzar(Integer.parseInt(cmdSplit[1]));
                return MessageType.ACK.name() + " " + MessageType.CZARCHANGE.name();
            }
        }
        else if(cmdSplit[0].equals(MessageType.BLACKCARD.name())){
            if(cmdSplit.length == 3){
                int numberCards = Integer.parseInt(cmdSplit[1]);
                String blackcard = cmdSplit[2];
                logic.setBlackCard(new BlackCard(blackcard, numberCards));
                return MessageType.ACK.name() + " " + MessageType.BLACKCARD.name();
            }
        }
        else if(cmdSplit[0].equals(MessageType.WHITECARDPICK.name())){
            if(cmdSplit.length < 4){
                int sender = Integer.parseInt(cmdSplit[1]);
                ArrayList<WhiteCard> res = new ArrayList<>();
                for (int j = 3; j < cmdSplit.length; j++){
                    res.add(new WhiteCard(cmdSplit[j], logic.getPlayers().get(sender)));
                }
                logic.addWhiteCardToBoard(res);
                return MessageType.ACK.name() + " " + MessageType.WHITECARDPICK.name();
            }
        }
        else if(cmdSplit[0].equals(MessageType.WINNERPICK.name())){
            if(cmdSplit.length == 2){
                logic.getPlayers().get(Integer.parseInt(cmdSplit[1])).addPoints(1);
                return MessageType.ACK.name() + " " + MessageType.WINNERPICK.name();
            }
        }
        else if(cmdSplit[0].equals(MessageType.RETRIEVEWHITECARD.name())){
            if(cmdSplit.length == 2){
                logic.getMe().addCard(new WhiteCard(cmdSplit[1], logic.getMe()));
                return MessageType.ACK.name() + " " + MessageType.RETRIEVEWHITECARD.name();
            }
        }
        else{
            return "error";
        }

        return "";
    }


}