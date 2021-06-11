import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Logic {
    int iteration;
    ArrayList<Move> listOfMoves = new ArrayList<>();
    MoveLogic moveLogic = new MoveLogic();
    Talon talons;
    ArrayList<BuildStack> board = new ArrayList<>();

    public void run() {
        BuildStackHolder buildStackHolder = new BuildStackHolder(board);
        Suit suits = new Suit();
        Move move = new Move();

        checkForMoves(move, buildStackHolder, talons, suits);
        Move max = new Move();
        for (Move maxCheck : listOfMoves) {
            if (max.point < maxCheck.point) {
                max = maxCheck;
            }
        }
        System.out.println("Max: " + max);
    }

    public void setUp() {
        ArrayList<Card> deckCards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card(true, 13, Type.Heart));
        cards.add(new Card(true, 10, Type.Heart));
        cards.add(new Card(true, 6, Type.Spade));
        cards.add(new Card(true, 10, Type.Diamond));
        cards.add(new Card(true, 10, Type.Spade));
        cards.add(new Card(true, 13, Type.Clover));
        cards.add(new Card(true, 5, Type.Diamond));
        setUpStacks(cards);

        deckCards.add(new Card(true, 1, Type.Heart));
        deckCards.add(new Card(true, 11, Type.Diamond));
        deckCards.add(new Card(true, 12, Type.Diamond));
        deckCards.add(new Card(true, 11, Type.Spade));
        deckCards.add(new Card(true, 11, Type.Clover));
        deckCards.add(new Card(true, 6, Type.Diamond));
        deckCards.add(new Card(true, 12, Type.Spade));
        deckCards.add(new Card(true, 8, Type.Spade));
        deckCards.add(new Card(true, 3, Type.Spade));
        deckCards.add(new Card(true, 3, Type.Clover));
        deckCards.add(new Card(true, 8, Type.Clover));
        deckCards.add(new Card(true, 9, Type.Spade));
        deckCards.add(new Card(true, 7, Type.Diamond));
        deckCards.add(new Card(true, 4, Type.Diamond));
        deckCards.add(new Card(true, 5, Type.Clover));
        deckCards.add(new Card(true, 7, Type.Heart));
        deckCards.add(new Card(true, 2, Type.Heart));
        deckCards.add(new Card(true, 12, Type.Heart));
        deckCards.add(new Card(true, 13, Type.Spade));
        deckCards.add(new Card(true, 4, Type.Spade));
        deckCards.add(new Card(true, 6, Type.Clover));
        deckCards.add(new Card(true, 4, Type.Heart));
        deckCards.add(new Card(true, 8, Type.Heart));
        deckCards.add(new Card(true, 12, Type.Clover));
        talons = new Talon(deckCards);

    }

    public void insertCard(Card toInsert, Card toInsertOn, BuildStackHolder stacks, Suit suit) {
        for (BuildStack stack : stacks.getStackList()) {
            //this stack contains card1
            if (stack.getStackLeader().getDocker().compareCards(toInsertOn)) {
                stack.getStackLeader().getBlock().add(toInsert);
                return;
            }
        }
        if (suit.getSuit(toInsertOn.getType().getValue()).contains(toInsertOn)) {
            suit.getSuit(toInsertOn.getType().getValue()).add(toInsert);
        }
    }

    public void removeCard(Card card, BuildStackHolder stacks, Talon talon, Suit suit) {
        if (stacks.removeCard(card))
            return;
        else if (talon.removeCard(card))
            return;
        else
            suit.removeCard(card);
    }

    public void turnCard(Card card, BuildStackHolder stacks) {
        for (BuildStack stack : stacks.getStackList()) {
            //this stack contains card1
            if (stack.getStackLeader().blockContains(card) != -1) {
                stack.getStackLeader().getLeader().setFaceUp();
            }
        }
    }

    public void performMove(Move moveArrayList, BuildStackHolder stacks, Talon talon, Suit suit) {
        LinkedList<Card> cardsToMove = new LinkedList<>();
        if (moveArrayList.hasMoves()) {
            cardsToMove = moveArrayList.getSimMove();
//            cardsToMove = moveArrayList.getSim();
            Card card1 = cardsToMove.poll();
            Card card2 = cardsToMove.poll();

            if (card1 == card2)
                turnCard(card1, stacks);
            else {
                removeCard(card1, stacks, talon, suit);
                insertCard(card1, card2, stacks, suit);
            }
        }
    }

    public void checkForMoves(Move move, BuildStackHolder holder, Talon talon, Suit suit) {
//        System.out.println(iteration++);
        if (move == null)
            return;
        performMove(move, holder, talon, suit);
        ArrayList<BuildStack> stackArray = holder.getStackList();
        //checks the board for internal moves
        for (int i = 0; i < 7; i++) {
            checkForMoves(moveLogic.checkStackToSuit(suit, stackArray.get(i), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            for (int j = i + 1; j < 7; j++) {
                //checking if stack 1 shares any moves
                checkForMoves(moveLogic.checkInternalMove(stackArray.get(i), stackArray.get(j), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            }
        }

        //checks for unturned cards
        Move temp = new Move();
        for (int i = 0; i < 7; i++) {
            temp = moveLogic.unTurnedCard(stackArray.get(i), temp);
        }
        if (temp.hasMoves()) {
            move.add(temp);
            System.out.println("Move Combination Found: " + move);
            listOfMoves.add(move);
            return;
        }
        //checks deck moves as last
        if (!move.hasMoves()) {
                checkForMoves(moveLogic.findTalonMove(talon, stackArray, suit, move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
        }
        System.out.println("Move Combination Found: " + move);
        listOfMoves.add(move);
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
            board.get(i).setIndex(i);
        }
    }
}
