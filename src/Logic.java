import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

/** This Class contains all the logic around the running of the algorithm */
public class Logic {
    //The different variables used throughout the class
    ArrayList<Move> listOfMoves = new ArrayList<>();
    MoveLogic moveLogic = new MoveLogic();
    Talon talons;
    ArrayList<BuildStack> board = new ArrayList<>();
    BuildStackHolder buildStackHolder;

    /**The method called to run the algorithm*/
    public void run() {
        buildStackHolder = new BuildStackHolder(board);
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

    /**  For now a method for setting up the game hardcoded #Todo Should make this automated and not hardcoded*/
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

    /** This method is used to insert the card into the new position where we want it either in the build stack or in
     * the suit!
     * @param toInsert The card we want inserted
     * @param toInsertOn The targeted card we want our toInsert card inserted onto
     * @param stacks The stack which this operation should be done on
     * @param suit The suit which this operation should be done on*/
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

    public void insertSubBlock(Card toInsertOn, BuildStackHolder stacks) {
        for (BuildStack stack : stacks.getStackList()) {
            //this stack contains card1
            if (stack.getStackLeader().getDocker().compareCards(toInsertOn)) {
                stack.getStackLeader().splitedBlock(stack.getStackLeader(), toInsertOn);
                return;
            }
        }
    }

    /** This method is used to remove a card by calling the different classes remove card functions
     * @param card The card to be removed
     * @param stacks The stack which the operation should be done on
     * @param  talon The talon which the operation should be done on
     * @param suit The suit which the operation should be done on*/
    public void removeCard(Card card, BuildStackHolder stacks, Talon talon, Suit suit) {
        //checks if the card we want removed is in one of the build stacks if it is we remove it
        if (stacks.removeCard(card)) {}
            //checks if the card we want removed is in the talon if it is we remove it
        else if (talon.removeCard(card)) {}
        else
            //else we will attempt to remove the card from the suit in case none of the other options contained the card
            suit.removeCard(card);
    }

    /** This method simply just finds the stack of the card that need to be turned and set's the face up value of the
     * card to true
     * @param card The card which we want turned
     * @param stacks The stacks which this operation should be done on*/
    public void turnCard(Card card, BuildStackHolder stacks) {
        for (BuildStack stack : stacks.getStackList()) {
            //this stack contains card1
            if (stack.getStackLeader().blockContains(card) != -1) {
                stack.getStackLeader().getLeader().setFaceUp();
            }
        }
    }

    /** This method is used to virtually perform the move found in the earlier iteration so the algorithm can
     * continue on as if the move had been done to check further than just the current move
     * @param movesToPerform The list of moves which need to be performed
     * @param stacks The stacks which the moves need to be performed on
     * @param talon The talon which the moves need to be performed on
     * @param suit The suit which the moves need to be performed on*/
    public void performMove(Move movesToPerform, BuildStackHolder stacks, Talon talon, Suit suit) {
        if (movesToPerform.hasMoves()) {
            LinkedList<Card> cardsToMove = movesToPerform.getSimMoves();
            Block block = new Block();
            Card card1 = cardsToMove.poll();
            Card card2 = cardsToMove.poll();
            if (card1 == card2)
                turnCard(card1, stacks);
            else {
                removeCard(card1, stacks, talon, suit);
                if (block.getBlock().size()>1)
                    //misforstået
                    insertSubBlock(card1,stacks);
                else
                    insertCard(card1, card2, stacks, suit);
            }
        }
    }

    /** The main method of the algorithm which works as an recursive function
     * @param move The move object used through the iterations to find all possible moves
     * @param holder The Build Stack list which contains all the build stacks
     * @param talon The talon to be used through the different iterations of the algorithm
     * @param suit The suit to be used through the different iterations of the algorithm
     * */
    public void checkForMoves(Move move, BuildStackHolder holder, Talon talon, Suit suit) {
        //checks if the move sent on was null if that's the cast we can go no longer in this part of the route
        if (move == null)
            return;
        //we simulate the performing of moves contained in the move chain list
        performMove(move, holder, talon, suit);
        ArrayList<BuildStack> stackArray = holder.getStackList();
        //checks the board for internal moves
        for (int i = 0; i < 7; i++) {
            //checking for moves from the stack that can lead to card being inserted into the suit
            checkForMoves(moveLogic.checkStackToSuit(suit, stackArray.get(i), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            for (int j = i + 1; j < 7; j++) {
                //Checking for possible moves internally between the stacks
                checkForMoves(moveLogic.checkInternalMove(stackArray.get(i), stackArray.get(j), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            }
        }

        //checks for unturned cards
        Move temp = new Move();
        for (int i = 0; i < 7; i++) {
            temp = moveLogic.unTurnedCard(stackArray.get(i), temp);
        }
        //if the algorithm found unturned cards we instantly ask the algorithm to stop searching on
        if (temp.hasMoves()) {
            move.add(temp);
            System.out.println("Move Combination Found: " + move);
            listOfMoves.add(move);
            return;
        }
        //checks for deck moves as the last thing
        if (!move.hasMoves()) {
                checkForMoves(moveLogic.findTalonMove(talon, stackArray, suit, move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
        }
        System.out.println("Move Combination Found: " + move);
        listOfMoves.add(move);
    }

    /** This method simply setup all the stacks with their cards turned and unturned
     * @param cards the List of cards going into the build stacks*/
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

    public int amountOfUnturnedCards(){

        int amountOfFaceDownCard = 0;

        for (int i = 0; i < buildStackHolder.getStackList().size(); i++) {
            if(buildStackHolder.getStackList().get(i).getStackLeader().getLeader().getType()==Type.Unturned)
                amountOfFaceDownCard+=1;
        }
        return amountOfFaceDownCard;
    }
}
