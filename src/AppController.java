import java.util.ArrayList;

public class AppController {
    Logic logic = new Logic();

    public void setUpGame(ArrayList<Card> initialBoardCards, ArrayList<Card> initialDeckCards) {
        logic.setUp();
        logic.setUpStacks(initialBoardCards);
    }

    public int cardsToTurn() {
        return logic.amountOfUnturnedCards();
    }


}
