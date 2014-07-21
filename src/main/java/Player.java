/**
 * Created by minh on 7/15/14.
 */

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Instance of this class represents a real life player
 * Each player is a thread and should be carefully manage TODO
 **/

public class Player implements Runnable, Serializable{

    private String name;
    private transient Socket socket;
    private transient InputStream is;
    private transient OutputStream os;
    private transient ObjectInputStream ois = null;
    private transient ObjectOutputStream oos = null;
    private ArrayList<Integer> playingCards;
    private CardDeck cardDeck;
    private boolean isPlaying;
    private boolean isDone;
    private boolean isWaiting;

    public Player(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        cardDeck = new CardDeck();
        playingCards = new ArrayList<Integer>();
    }

    public void writeToClient(Object input){
        try {
            oos.writeObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CardDeck getCardDeck(){
        return this.cardDeck;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    @Override
    public void run() {

        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            ois = new ObjectInputStream(is);
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
