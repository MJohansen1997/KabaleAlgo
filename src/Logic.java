import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Logic {
    int testCounter = 0;
    ArrayList<Move> listOfMoves = new ArrayList<>();

    Suit suits = new Suit();
    MoveLogic moveLogic = new MoveLogic();
    Move movesList = new Move();
    Talon talons;
    BuildStackHolder buildStackHolder;

    public void run() {
        checkForMoves(movesList, buildStackHolder, talons, suits);
    }
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
        talons = new Talon(deckCards);

    }

    public void checkForMoves(Move movesList, BuildStackHolder stackArray, Talon talon, Suit suit) {
        if (movesList == null)
            return;
        BuildStackHolder tempBuildstacks = new BuildStackHolder(stackArray);
        System.out.println(++testCounter);

        for (int i = 0; i < 7; i++) {
//            tempBuildstacks = new ArrayList<>(stackArray);
//            System.out.println("test:"+i);
            checkForMoves(moveLogic.checkStackToSuit(suit, tempBuildstacks.stacks.get(i), movesList), tempBuildstacks, talon, suit);
            for (int j = i + 1; j < 7; j++) {
                //checking if stack 1 shares any moves
                checkForMoves(moveLogic.checkInternalMove(tempBuildstacks.stacks.get(i), tempBuildstacks.stacks.get(j), movesList), tempBuildstacks, talon, suit);
            }
        }
        //defines a scope so temp only exist inside this scope
        {
            boolean temp = false;
            for (int i = 0; i < 7; i++) {

                if (moveLogic.unTurnedCard(tempBuildstacks.stacks.get(i), movesList) != null) {
                    listOfMoves.add(movesList);
                    temp = true;
                }
            }
            if (temp)
                return;
        }
        //checks deck moves as last
        for (int i = 0; i < talon.getDeck().size(); i++) {
            checkForMoves(moveLogic.findTalonMove(i, talon, tempBuildstacks.stacks, suit, movesList), tempBuildstacks, talon, suit);
        }


        listOfMoves.add(movesList);
        //System.out.println(listOfMoves.size());
    }

    private void setUpStacks(ArrayList<Card> cards) {
        ArrayList<BuildStack> board = new ArrayList<>();
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
            board.add(new BuildStack(blocks));
            buildStackHolder = new BuildStackHolder(board);
        }
    }
}
