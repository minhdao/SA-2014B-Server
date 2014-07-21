/**
 * Created by minh on 7/15/14.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Instance of this class represents a real life player
 * Each player is a thread and should be carefully manage TODO
 **/

public class Player implements Runnable{

    private String name;
    private Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    private ArrayList<Integer> cardDeck;
    private ArrayList<Integer> playingCards;
    private boolean isPlaying;
    private boolean isDone;
    private boolean isWaiting;

    public Player(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        cardDeck = new ArrayList<Integer>();
        playingCards = new ArrayList<Integer>();
    }

    public void writeToClient(){
        try {
            dos.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCardDeck(ArrayList<Integer> cards){
        this.cardDeck = cards;
    }

    public void printCardDeck(){
        for (int i =0; i <cardDeck.size(); i++){
            System.out.println(cardDeck.get(i));
        }
    }

    public ArrayList<Integer> getCardDeck(){
        return this.cardDeck;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("Connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
