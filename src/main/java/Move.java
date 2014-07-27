import java.io.Serializable;
/**
 * Created by minh on 7/22/14.
 * This is the move of the current player
 * Each move is associated with the player and the cards played
 */
public class Move implements Serializable{

    private String player;
    private CardDeck cards;

    public Move(String player, CardDeck cards){
        this.player = player;
        this.cards = cards;
    }
}
