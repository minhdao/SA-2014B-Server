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

public class Player implements Runnable {

    private String name;
    private transient Socket socket;
    private Communicator communicator;
    private ArrayList<Integer> playingCards;
    private CardDeck cardDeck;

    public Player(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        try {
            communicator = new Communicator(socket,
                    new ObjectInputStream(socket.getInputStream()),
                    new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cardDeck = new CardDeck();
        playingCards = new ArrayList<Integer>();
    }


    public CardDeck getCardDeck(){
        return this.cardDeck;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    @Override
    public void run() {

    }
}
