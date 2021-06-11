import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Logic {
    ArrayList<Move> listOfMoves = new ArrayList<>();
    ArrayList<BuildStack> board = new ArrayList<>();
    BuildStackHolder buildStackHolder = new BuildStackHolder(board);
    Suit suits = new Suit();
    MoveLogic moveLogic = new MoveLogic();
    Talon talons;
    Move moves = new Move();

    public void run() {
        if (listOfMoves.size() == 0)
            checkForMoves(board, talons, suits, moves);
        for (Move move : listOfMoves) {
            ArrayList<BuildStack> temp = new BuildStackHolder(buildStackHolder).stacks;
            performMove(move.cloneObject(), temp);
            checkForMoves(board, talons.cloneTalon(), suits.cloneSuit(), move.cloneObject());
        }
        run();
    }

    public void setUp() {
        ArrayList<Card> deckCards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card(true, 12, Type.Diamond));
        cards.add(new Card(true, 2, Type.Heart));
        cards.add(new Card(true, 4, Type.Heart));
        cards.add(new Card(true, 2, Type.Diamond));
        cards.add(new Card(true, 5, Type.Spade));
        cards.add(new Card(true, 6, Type.Heart));
        cards.add(new Card(true, 1, Type.Spade));
        setUpStacks(cards);

        deckCards.add(new Card(true, 7, Type.Spade));
        deckCards.add(new Card(true, 1, Type.Clover));
        deckCards.add(new Card(true, 6, Type.Spade));
        deckCards.add(new Card(true, 12, Type.Clover));
        deckCards.add(new Card(true, 10, Type.Diamond));
        deckCards.add(new Card(true, 8, Type.Clover));
        deckCards.add(new Card(true, 9, Type.Diamond));
        deckCards.add(new Card(true, 12, Type.Spade));
        deckCards.add(new Card(true, 3, Type.Heart));
        deckCards.add(new Card(true, 2, Type.Spade));
        deckCards.add(new Card(true, 11, Type.Heart));
        deckCards.add(new Card(true, 8, Type.Spade));
        deckCards.add(new Card(true, 11, Type.Spade));
        deckCards.add(new Card(true, 7, Type.Heart));
        deckCards.add(new Card(true, 12, Type.Heart));
        deckCards.add(new Card(true, 11, Type.Diamond));
        deckCards.add(new Card(true, 3, Type.Diamond));
        deckCards.add(new Card(true, 10, Type.Heart));
        deckCards.add(new Card(true, 9, Type.Heart));
        deckCards.add(new Card(true, 9, Type.Clover));
        deckCards.add(new Card(true, 13, Type.Diamond));
        deckCards.add(new Card(true, 9, Type.Spade));
        deckCards.add(new Card(true, 7, Type.Clover));
        deckCards.add(new Card(true, 6, Type.Clover));
        talons = new Talon(deckCards);

    }

    public void insertCard(Card toInsert, Card toInsertOn, ArrayList<BuildStack> stacks) {
        for (BuildStack stack : stacks) {
            //this stack contains card1
            if (stack.getStackLeader().getDocker() == toInsertOn) {
                stack.getStackLeader().getBlock().add(toInsert);
                return;
            }
        }
        if (suits.getSuit(toInsertOn.getType().getValue()).contains(toInsertOn)) {
            suits.getSuit(toInsertOn.getType().getValue()).add(toInsert);
        }
    }

    public void removeCard(Card card, ArrayList<BuildStack> stacks) {
        for (BuildStack stack : stacks) {
            //this stack contains card1
            int index = stack.getStackLeader().getBlock().indexOf(card);
            if (index == 0) {
                stack.getStack().remove(stack.getStackLeader());
                return;
            }
            else if (index != -1) {
                //if the card isn't in leader position
            }
            else {
            }
        }
        if (talons.getDeck().remove(card)) {
            return;
        }

        if (suits.getTop(card.getType().getValue()) == card) {
            suits.getSuit(card.getType().getValue()).remove(card);
        }
    }

    public void performMove(Move moveArrayList, ArrayList<BuildStack> stacks) {
        LinkedList<Card> cardsToMove = new LinkedList<>();
        if (moveArrayList.hasMoves()) {
            cardsToMove = moveArrayList.getMove();
//            cardsToMove = moveArrayList.getSim();
            Card card1 = cardsToMove.poll();
            Card card2 = cardsToMove.poll();

            insertCard(card1, card2, stacks);
            removeCard(card1, stacks);
        }
    }

    public void checkForMoves(ArrayList<BuildStack> stackArray, Talon talon, Suit suit, Move move) {
        performMove(move, stackArray);
        Move tempMove = new Move(move);
        for (int i = 0; i < 7; i++) {
           tempMove = moveLogic.checkStackToSuit(suit, stackArray.get(i));
            if (tempMove != null)
                listOfMoves.add(tempMove);
            for (int j = i + 1; j < 7; j++) {
                //checking if stack 1 shares any moves
                tempMove = moveLogic.checkInternalMove(stackArray.get(i), stackArray.get(j));
                if (tempMove != null)
                    listOfMoves.add(tempMove);
            }
        }
        //defines a scope so temp only exist inside this scope
        {
            boolean temp = false;
            for (int i = 0; i < 7; i++) {
                if (moveLogic.unTurnedCard(stackArray.get(i), move) != null) {
                    listOfMoves.add(tempMove);
                    temp = true;
                }
            }
            if (temp)
                return;
        }
        //checks deck moves as last
        for (int i = 0; i < talon.getDeck().size(); i++) {
            tempMove = moveLogic.findTalonMove(i, talon, stackArray, suit);
            if (tempMove != null)
                listOfMoves.add(tempMove);
        }
    }

    private void setUpStacks(ArrayList<Card> cards) {
        for (int i = 0; i <= 6; i++) {
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
        }
    }
}
