import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Logic {
    ArrayList<BuildStack> buildstacks = new ArrayList<>();
    Suit suit = new Suit();
    MoveLogic moveLogic = new MoveLogic();
    Talon talon;


    public void setUp() {
        ArrayList<Card> deckCards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card(true, 12, Type.Diamond));
        cards.add(new Card(true, 2, Type.Heart));
        cards.add(new Card(true, 4, Type.Heart));
        cards.add(new Card(true, 2, Type.Diamond));
        cards.add(new Card(true, 5,  Type.Spade));
        cards.add(new Card(true, 6, Type.Heart));
        cards.add(new Card(true, 1,  Type.Spade));
        setUpStacks(cards);

        deckCards.add(new Card(true, 7,  Type.Spade));
        deckCards.add(new Card(true, 1,  Type.Clover));
        deckCards.add(new Card(true, 6,  Type.Spade));
        deckCards.add(new Card(true, 12,  Type.Clover));
        deckCards.add(new Card(true, 10, Type.Diamond));
        deckCards.add(new Card(true, 8,  Type.Clover));
        deckCards.add(new Card(true, 9,  Type.Diamond));
        deckCards.add(new Card(true, 12,  Type.Spade));
        deckCards.add(new Card(true, 3,  Type.Heart));
        deckCards.add(new Card(true, 2,  Type.Spade));
        deckCards.add(new Card(true, 11,  Type.Heart));
        deckCards.add(new Card(true, 8,  Type.Spade));
        deckCards.add(new Card(true, 11,  Type.Spade));
        deckCards.add(new Card(true, 7,  Type.Heart));
        deckCards.add(new Card(true, 12,  Type.Heart));
        deckCards.add(new Card(true, 11,  Type.Diamond));
        deckCards.add(new Card(true, 3,  Type.Diamond));
        deckCards.add(new Card(true, 10,  Type.Heart));
        deckCards.add(new Card(true, 9,  Type.Heart));
        deckCards.add(new Card(true, 9,  Type.Clover));
        deckCards.add(new Card(true, 13,  Type.Diamond));
        deckCards.add(new Card(true, 9,  Type.Spade));
        deckCards.add(new Card(true, 7,  Type.Clover));
        deckCards.add(new Card(true, 6,  Type.Clover));
        talon = new Talon(deckCards);

    }

    public void checkForMoves() {
        for (int i = 0; i < 7; i++) {
            moveLogic.checkStackToGoal(suit, buildstacks.get(i));
            for (int j = i+1; j < 6; j++) {
                //checking if stack 1 shares any moves
                moveLogic.checkInternalMove(buildstacks.get(i), buildstacks.get(j));
            }
        }
        //checks deck moves as last
        moveLogic.findDeckMove(talon, buildstacks, suit);
    }

    private void setUpStacks(ArrayList<Card> cards) {
        for (int i = 0; i <= 6 ; i++) {
            ArrayList<Block> blocks = new ArrayList<>();
            for (int j = i; j > 0; j--) {
                LinkedList<Card> temp = new LinkedList<>();
                temp.add(new Card(false));
                blocks.add(new Block(temp));
            }
            LinkedList<Card> temp = new LinkedList<>();
            temp.add(cards.get(i));
            blocks.add(new Block(temp));
            buildstacks.add(new BuildStack(blocks));
        }
    }
}
