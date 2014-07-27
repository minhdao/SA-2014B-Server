import javax.smartcardio.Card;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shinichi on 21/07/2014.
 */
public class CardDeck implements Serializable{


    private ArrayList<Integer> cards = new ArrayList<Integer>();

    public ArrayList<Integer> getCards() {
        return cards;
    }

}
