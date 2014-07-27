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
    private Move previousMove = null;
    private Player currentPlayer;

    public CardTable(){
        players = new ArrayList<Player>();
        cardDeck = new CardDeck();
    }

    // add player into the players array
    public void addPlayer(Player player){

        if (players.size() < 1){
            players.add(player);
            count++;
        }

        // TODO should be changed when the game is complete
        if (count == 1){
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
            for(int j = 0; j < players.size(); j++){
                players.get(j).getCardDeck().getCards().add(this.cardDeck.getCards().get(i));
                i++;
            }
        }
    }

    // method to start player threads
    private void startThreads(){
        Thread thread;
        for (int i = 0; i < players.size(); i++){
            thread = new Thread(players.get(i));
            thread.start();
        }
    }

    // method to determine next player
    // contain logic to determine who the next player is
    private Player getNextPlayer(Player currentPlayer){

        if (currentPlayer.getTurnNumber()+1 < players.size()){
            int next = currentPlayer.getTurnNumber() + 1;
            System.out.println(next);
            return players.get(next);
        }else{
            return players.get(0);
        }

    }

    private Status validateMove(Move move){
        // the first one to move
        if (previousMove == null){
            if (move.getCards().getCards().size() == 1){
                previousMove = move;
                return Status.Valid;
            } else {

            }
        }
        return Status.Valid;
    }

    // this is where the game begins to run
    private void startGame(){
        // shuffle and deal cards to all players
        shuffleCards();
        dealCards();
        startThreads();
        // set currentPlayer to be the first one in the array list
        currentPlayer = players.get(0);
        // keep the game alive until some conditions are met
        while (true){
            Object message = currentPlayer.getCommunicator().read(); // wait to read from the current player
            if (message instanceof Test){
                Test test = (Test) message;
                System.out.println(test.getMessage());
                currentPlayer.getCommunicator().write(new Test("hello, " + test.getMessage()));
                currentPlayer = getNextPlayer(currentPlayer);
            } else if (message instanceof Move){
                Move move = (Move) message;

                // code to see what inside a move
                System.out.println("Move received");
                System.out.println("Player: " + move.getPlayerName());
                System.out.println("Cards: ");
                for (int i =0; i < move.getCards().getCards().size();i++){
                    System.out.println(move.getCards().getCards().get(i));
                }

            } else if (message instanceof String){
                String name = (String) message;
                currentPlayer.getCommunicator().write(currentPlayer.getCardDeck());
                currentPlayer = getNextPlayer(currentPlayer);
            }

            // get next player
            System.out.println(currentPlayer.getName());
        }
    }
}
