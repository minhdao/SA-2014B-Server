import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by minh on 7/22/14.
 * This is the move of the current player
 * Each move is associated with the player and the cards played
 */
public class Move implements Serializable{

    private Player player;
    private ArrayList<Integer> cards;

    public Move(Player player, ArrayList<Integer> cards){
        this.player = player;
        this.cards = cards;
    }
}
