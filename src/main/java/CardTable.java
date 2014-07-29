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
    private ArrayList<Integer> two = new ArrayList<Integer>(){{add(150); add(151); add(152); add(153);}};
    private CardDeck cardDeck;
    private Move previousMove = null;
    private Player currentPlayer;
    private static int type = 1;

    public CardTable(){
        players = new ArrayList<Player>();
        cardDeck = new CardDeck();
    }

    // add player into the players array
    public void addPlayer(Player player){

        if (players.size() < 2){
            players.add(player);
            count++;
        }

        // TODO should be changed when the game is complete
        if (players.size() == 2){
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
        for (int i =0; i<26;){
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

    private Status validateMove(Move curMove){
//        previousMove = move;
//        previousMove.setType(Status.PreviousMove);

        int curSize = curMove.getCards().getCards().size();
        if(curSize != 0){
            //first move of the match
            if(previousMove == null){
                if(curSize > 1){
                    if(checkSame(curMove.getCards(), curSize)){
                        type = 1;
                        return confirmMove(curMove);
                    }else if(checkBlock(curMove.getCards(), curSize)){
                        type = 2;
                        return confirmMove(curMove);
                    }
                }else{
                    return confirmMove(curMove);
                }
            }
            //next moves
            else{
                int previousSize = previousMove.getCards().getCards().size();

                if(previousSize == curSize){
                    //compare current to previous move
                    if(type == 1){
                        if(checkSame(curMove.getCards(), curSize)){
                            if(curMove.getCards().getCards().get(curSize-1) - previousMove.getCards().getCards().get(previousSize-1) > 0){
                                return confirmMove(curMove);
                            }
                        }
                    }else if(type == 2){
                        if(checkBlock(curMove.getCards(), curSize)){
                            if(curMove.getCards().getCards().get(curSize-1) - previousMove.getCards().getCards().get(previousSize-1) > 0){
                                return confirmMove(curMove);
                            }
                        }
                    }else{
                        if(curMove.getCards().getCards().get(0) - previousMove.getCards().getCards().get(0) > 0){
                            return confirmMove(curMove);
                        }
                    }
                }//kill 2s
                else if(two.containsAll(previousMove.getCards().getCards()) && previousSize <= 2){
                    if(checkSame(curMove.getCards(), curSize) && curSize == 4){
                        return confirmMove(curMove);
                    }else if(curSize >= 6 && checkThong(curMove.getCards(), curSize)){
                        return confirmMove(curMove);
                    }
                }else{
                    return Status.Invalid;
                }
            }
        }
        return Status.Invalid;
    }

    //valid moves
    public Status confirmMove(Move curMove){
        previousMove = curMove;
        previousMove.setType(Status.PreviousMove);
        updatePrevious(previousMove);
        currentPlayer = getNextPlayer(currentPlayer);
        currentPlayer.getCommunicator().write(Status.Continue);
        currentPlayer.getCommunicator().write(Status.YourTurn);
        return Status.Valid;
    }

    //update previous to all players
    public void updatePrevious(Move previousMove){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getCommunicator().write(previousMove);
        }
    }

    //sort cards
    public CardDeck sortCards(CardDeck cards){
        Collections.sort(cards.getCards());
        return cards;
    }

    //check same cards block
    public boolean checkSame(CardDeck cards, int size){
        int temp = cards.getCards().get(0)/10;
        for (int i = 0; i < size; i++) {
            if(cards.getCards().get(i) / 10 != temp){
                return false;
            }
        }
        return true;
    }

    //check 3 doi thong
    public boolean checkThong(final CardDeck cards, int size){
        int temp = cards.getCards().get(0)/10;
        for (int i = 0; i < size; i+=2) {
            if(i+1 < size){
                if(cards.getCards().get(i+1) - cards.getCards().get(i) <= 3){
                    int prefix = cards.getCards().get(i) / 10;
                    if(prefix - temp == 1){
                        temp++;
                    }
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    //check continuous block
    public boolean checkBlock(CardDeck cards, int size){
        int temp = cards.getCards().get(0)/10;
        for (int i = 1; i < size; i++) {
            int prefix = cards.getCards().get(i) / 10;
            if(prefix - temp == 1){
                temp++;
            }else{
                return false;
            }
        }
        return true;
    }

    // this is where the game begins to run
    private void startGame(){
        // shuffle and deal cards to all players
        shuffleCards();
        dealCards();
        startThreads();
        // send card decks back to player
        for (int i =0; i<players.size();i++){
            players.get(i).getCommunicator().write(players.get(i).getCardDeck());
        }
        // set currentPlayer to be the first one in the array list
        currentPlayer = players.get(0);
        currentPlayer.getCommunicator().write(Status.YourTurn);
//        for (int i = 0; i< players.size(); i++){
//            if (currentPlayer == players.get(i)){
//                currentPlayer.getCommunicator().write(Status.YourTurn);
//            } else {
//                players.get(i).getCommunicator().write(Status.Wait);
//            }
//        }
        // keep the game alive until some conditions are met
        Object message;
        while (true){

            message = currentPlayer.getCommunicator().read(); // wait to read from the current player

//            if (message instanceof Test){
//                Test test = (Test) message;
//                System.out.println(test.getMessage());
//                currentPlayer.getCommunicator().write(new Test("hello, " + test.getMessage()));
//                currentPlayer = getNextPlayer(currentPlayer);
//            }

            if (message instanceof Move){
                Move move = (Move) message;

                // code to see what inside a move
                System.out.println("------------Move received--------------");
                System.out.println("Player: " + move.getPlayerName());
                System.out.print("Cards: ");
                for (int i =0; i < move.getCards().getCards().size();i++){
                    System.out.print(move.getCards().getCards().get(i) + " ");
                }
                System.out.println();

                // write back status of the move to current player
                currentPlayer.getCommunicator().write(validateMove(move));

            }else if(message instanceof Status){
                Status status = (Status)message;
                if(status == Status.Pass){
                    currentPlayer.getCommunicator().write(Status.Wait);
                    currentPlayer = getNextPlayer(currentPlayer);
                    currentPlayer.getCommunicator().write(Status.Continue);
                }
            }

//            if (message instanceof String){
//                String name = (String) message;
//                currentPlayer.getCommunicator().write(currentPlayer.getCardDeck());
////                currentPlayer = getNextPlayer(currentPlayer);
//            }

//            message = currentPlayer.getCommunicator().read();

            // get next player
//            System.out.println(currentPlayer.getName());
        }
    }
}
