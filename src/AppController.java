import java.util.ArrayList;
/**
 * @author Gruppe 13 - Jacob Christensen s174130, Mads Hansen s195456, Mikkel Johansen s175194, Shania Hau s195477, Stefan Luxhøj s195467
 **/
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
