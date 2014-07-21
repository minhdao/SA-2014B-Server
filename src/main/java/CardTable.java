import java.io.IOException;
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
    private ArrayList<Integer> cardDeck = new ArrayList<Integer>();

    public CardTable(){
        players = new ArrayList<Player>();
    }

    // add player into the players array
    public void addPlayer(Player player){

        if (players.size() < 4){
            players.add(player);
            players.get(count).run();
            count++;
        }

        if (count == 4){
            startGame();
        }
    }

    public void shuffer(){
        for (int i = 3; i <= 15; i++){
            int temp = i * 10;
            for (int j = 0; j < 4; j++){
                cardDeck.add(temp + j);
            }
        }
        Collections.shuffle(cardDeck);
        int count = 0;
        for (int i = 0; i < 52; i++){
            System.out.print(cardDeck.get(i)+"            ");
            count++;
            if (count % 4 == 0){
                System.out.println();
            }
        }
    }

    private void startGame(){
        dealCards();
        try {
            players.get(0).getOos().writeObject(players.get(0).getCardDeck());
            players.get(1).getOos().writeObject(players.get(1).getCardDeck());
            players.get(2).getOos().writeObject(players.get(2).getCardDeck());
            players.get(3).getOos().writeObject(players.get(3).getCardDeck());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < players.size(); i++) {
//            players.get(i).writeToClient(players.get(i));
//        }
//        while (true){
//            players.get(0).writeToClient();
//        }
    }

    private void dealCards(){
        for (int i =0; i<cardDeck.size();){
            for(int j = 0; j < players.size(); j++){
                players.get(j).getCardDeck().getCards().add(cardDeck.get(i));
                i++;
            }
        }
    }

    public int getPlayerNumber(){
        return players.size();
    }
}
