import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by minh on 7/15/14.
 * CardTable can hold 4 players
 * Contain the logic for the game
 */

public class CardTable {

    private int count = 0;
    private ArrayList<Player> players;
    private CardDeck cardDeck;
    private Player currentPlayer;

    public CardTable(){
        players = new ArrayList<Player>();
        cardDeck = new CardDeck();
    }

    // add player into the players array
    public void addPlayer(Player player){

        if (players.size() < 4){
            players.add(player);
            players.get(count).run();
            count++;
        }

        // TODO should be changed when the game is complete
        if (count != 0){
            startGame();
        }
    }

    // shuffle the cards before the game start
    public void shuffleCards(){
        for (int i = 3; i <= 15; i++){
            int temp = i * 10;
            for (int j = 0; j < 4; j++){
                cardDeck.getCards().add(temp + j);
            }
        }
        Collections.shuffle(cardDeck.getCards());
        int count = 0;
        for (int i = 0; i < 52; i++){
            System.out.print(cardDeck.getCards().get(i)+"\t\t");
            count++;
            if (count % 4 == 0){
                System.out.println();
            }
        }
    }

    // deal cards to all players
    private void dealCards(){
        for (int i =0; i<13;){
            players.get(0).getCardDeck().getCards().add(this.cardDeck.getCards().get(i));
//            for(int j = 0; j < players.size(); j++){
//                players.get(j).getCardDeck().getCards().add(this.cardDeck.get(i));
//                i++;
//            }
            i++;
        }
    }

    // this is where the game begins to run
    private void startGame(){
        // shuffle and deal cards to all players
        shuffleCards();
        dealCards();
        // 3 lines below are used to test only
        Thread thread = new Thread(players.get(0));
        thread.start();
        players.get(0).getCommunicator().write(players.get(0).getCardDeck());
        // set currentPlayer to be the first one in the array list
        currentPlayer = players.get(0);
        // keep the game alive until some conditions are met
        while (true){
            Object test = currentPlayer.getCommunicator().read(); // wait to read from the current player
            break; // remove when needed
        }
    }
}
