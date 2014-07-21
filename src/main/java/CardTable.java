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
        while (true){
            players.get(0).writeToClient();
            dealCards();
        }
    }

    private void dealCards(){
        for (int i =0; i<cardDeck.size();){
            for(int j = 0; j < players.size(); j++){
                players.get(j).getCardDeck().add(cardDeck.get(i));
                System.out.print(players.get(j) +" "+ players.get(j).getCardDeck());
                ++i;
            }
            System.out.println();
        }
    }

    public int getPlayerNumber(){
        return players.size();
    }
}
