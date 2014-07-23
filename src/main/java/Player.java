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
    private Communicator communicator;
    private CardDeck playedCards;
    private CardDeck cardDeck;
    private int turnNumber;

    public Player(String name, Socket socket, int turnNumber){
        this.name = name;
        try {
            communicator = new Communicator(socket,
                    new ObjectInputStream(socket.getInputStream()),
                    new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.turnNumber = turnNumber;
        cardDeck = new CardDeck();
        playedCards = new CardDeck();
    }


    public CardDeck getCardDeck(){
        return this.cardDeck;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println(name + " " + "thread started!!!");
        // keep on listening to the client code
//        while(true){
//            communicator.read();
//            break; // remove this when needed
//        }
    }
}
