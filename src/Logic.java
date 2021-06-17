import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/** This Class contains all the logic around the running of the algorithm */
public class Logic {
    //The different variables used throughout the class
    ArrayList<Move> listOfMoves = new ArrayList<>();
    MoveLogic moveLogic = new MoveLogic();
    Talon talons;
    ArrayList<BuildStack> board = new ArrayList<>();
    BuildStackHolder buildStackHolder;
    Suit suits = new Suit();

    /**The method called to run the algorithm*/
    public void run() {
        listOfMoves = new ArrayList<>();
        Move move = new Move();

        checkForMoves(move, buildStackHolder, talons, suits);
        Move max = new Move();
        for (Move maxCheck : listOfMoves) {
            if (max.point < maxCheck.point) {
                max = maxCheck;
            }
//            if (max.point == maxCheck.point) {
//                if (max.moveList.size() > maxCheck.moveList.size()) {
//                    max = maxCheck;
//                }
//            }
        }
        if (max.moveList.size() == 0)
            return;

        System.out.println("Max: " + max);
//        performPermanentMoves(max);
//        insertEmpties();
//        run();
    }

    /**  For now a method for setting up the game hardcoded #Todo Should make this automated and not hardcoded*/
    public void setUp() {
        ArrayList<Card> deckCards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();

        setUpTestForKing(deckCards, cards);
//        setUpStandard(deckCards, cards);

    }

    private void setUpStandard(ArrayList<Card> deckCards, ArrayList<Card> cards) {
        cards.add(new Card(true, 8, Type.Spade));
        cards.add(new Card(true, 11, Type.Spade));
        cards.add(new Card(true, 2, Type.Clover));
        cards.add(new Card(true, 10, Type.Diamond));
        cards.add(new Card(true, 7, Type.Clover));
        cards.add(new Card(true, 12, Type.Diamond));
        cards.add(new Card(true, 9, Type.Diamond));
        setUpStacks(cards);

        deckCards.add(new Card(true, 1, Type.Spade));
        deckCards.add(new Card(true, 1, Type.Diamond));
        deckCards.add(new Card(true, 6, Type.Clover));
        deckCards.add(new Card(true, 5, Type.Diamond));
        deckCards.add(new Card(true, 11, Type.Clover));
        deckCards.add(new Card(true, 7, Type.Spade));
        deckCards.add(new Card(true, 1, Type.Heart));
        deckCards.add(new Card(true, 5, Type.Clover));
        deckCards.add(new Card(true, 1, Type.Clover));
        deckCards.add(new Card(true, 3, Type.Diamond));
        deckCards.add(new Card(true, 6, Type.Spade));
        deckCards.add(new Card(true, 7, Type.Diamond));
        deckCards.add(new Card(true, 4, Type.Clover));
        deckCards.add(new Card(true, 10, Type.Spade));
        deckCards.add(new Card(true, 10, Type.Heart));
        deckCards.add(new Card(true, 2, Type.Spade));
        deckCards.add(new Card(true, 5, Type.Spade));
        deckCards.add(new Card(true, 3, Type.Spade));
        deckCards.add(new Card(true, 8, Type.Diamond));
        deckCards.add(new Card(true, 4, Type.Diamond));
        deckCards.add(new Card(true, 11, Type.Heart));
        deckCards.add(new Card(true, 5, Type.Heart));
        deckCards.add(new Card(true, 12, Type.Spade));
        deckCards.add(new Card(true, 3, Type.Heart));
        talons = new Talon(deckCards);
    }

    private void setUpTestForKing(ArrayList<Card> deckCards, ArrayList<Card> cards) {

        cards.add(new Card(true, 0, Type.Heart));
        cards.add(new Card(true, 0, Type.Heart));
        cards.add(new Card(true, 13, Type.Spade));
        cards.add(new Card(true, 12, Type.Diamond));
        cards.add(new Card(true, 0, Type.Heart));
        cards.add(new Card(true, 0, Type.Diamond));
        cards.add(new Card(true, 0, Type.Clover));
        setUpStacks(cards);


        deckCards.add(new Card(true, 12, Type.Heart));
        deckCards.add(new Card(true, 13, Type.Heart));

        talons = new Talon(deckCards);
    }


    /** This method is used to insert the card into the new position where we want it either in the build stack or in
     * the suit!
     * @param toInsert The card we want inserted
     * @param toInsertOn The targeted card we want our toInsert card inserted onto
     * @param stacks The stack which this operation should be done on
     * @param suit The suit which this operation should be done on*/
    public void insertCard(Block toInsert, Card toInsertOn, BuildStackHolder stacks, Suit suit) {
        //First Check If toInsert is Leader If True insert whole block on toInsertOn's Block
        //If False, and toInsert isn't a Docker, Create new Sublist a New Block from toInsert's Index and remove
        //said sublist from the old block
        for (BuildStack stack : stacks.getStackList()) {
            //if a stack is empty and we're trying to move a king. Create a new block with king card
            if(toInsertOn.getType() == Type.Empty && stack.isStackEmpty()) {
                stack.getStack().add(new Block(toInsert));
                return;
            }

            //this stack contains card1
            if (stack.getStack().size() == 0)
                continue;
            if (stack.getStackLeader().getDocker().compareCards(toInsertOn)) {
                stack.getStackLeader().getBlock().addAll(toInsert.getBlock());
                return;
            }
        }
        if (suit.getSuit(toInsertOn.getType().getValue()).contains(toInsertOn)) {
            suit.getSuit(toInsertOn.getType().getValue()).addAll(toInsert.getBlock());
        }
    }

    /** This method is used to remove a card by calling the different classes remove card functions
     * @param card The card to be removed
     * @param stacks The stack which the operation should be done on
     * @param talon The talon which the operation should be done on
     * @param suit The suit which the operation should be done on*/
    public Block removeCard(Card card, BuildStackHolder stacks, Talon talon, Suit suit) {
        //checks if the card we want removed is in one of the build stacks if it is we remove it
        Block temp;
        temp = stacks.removeBlock(card);
        if (temp != null)
            return temp;
        //checks if the card we want removed is in the talon if it is we remove it
        temp = talon.removeCard(card);
        if (temp != null) {
            return temp;
        }
        //else we will attempt to remove the card from the suit in case none of the other options contained the card
        temp = suit.removeCard(card);
        return temp;
    }

    /** This method is used to virtually perform the move found in the earlier iteration so the algorithm can
     * continue on as if the move had been done to check further than just the current move
     * @param movesToPerform The list of moves which need to be performed
     * @param stacks The stacks which the moves need to be performed on
     * @param talon The talon which the moves need to be performed on
     * @param suit The suit which the moves need to be performed on*/
    public void performSimMove(Move movesToPerform, BuildStackHolder stacks, Talon talon, Suit suit) {
        while (movesToPerform.hasMoves()) {
            LinkedList<Card> cardsToMove = movesToPerform.getSimMoves();
            Card card1 = cardsToMove.poll();
            Card card2 = cardsToMove.poll();
            //Inserts and Removes the card from original location to it's new location
            insertCard(removeCard(card1, stacks, talon, suit), card2, stacks, suit);
        }
    }
    public void performPermanentMoves(Move movesToPerform){
        LinkedList<Card> cardsToMove = movesToPerform.moveList;
        while (cardsToMove.size() > 0){
            Card card1 = cardsToMove.poll();
            Card card2 = cardsToMove.poll();
            if (card1.compareCards(card2))
                continue;
            insertCard(removeCard(card1, buildStackHolder, talons, suits), card2, buildStackHolder, suits);
        }
        System.out.println("done");
    }
    public void insertEmpties(){
        for (BuildStack stacks : buildStackHolder.getStackList()) {
            Block stackLeader = stacks.getStackLeader();
            if (stackLeader == null)
                continue;
            Card card = stackLeader.getLeader();
            if (card.getType() == Type.Unturned) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("insert card from row : " + stacks.getIndex());
                card.setFaceUp(scanner.nextInt(), scanner.nextInt());
            }
        }
    }

    /** The main method of the algorithm which works as an recursive function
     * @param move The move object used through the iterations to find all possible moves
     * @param holder The Build Stack list which contains all the build stacks
     * @param talon The talon to be used through the different iterations of the algorithm
     * @param suit The suit to be used through the different iterations of the algorithm
     **/
    public void checkForMoves(Move move, BuildStackHolder holder, Talon talon, Suit suit) {
        //checks if the move sent on was null if that's the cast we can go no longer in this part of the route
        if (move == null)
            return;
        //we simulate the performing of moves contained in the move chain list
        performSimMove(move, holder, talon, suit);
        ArrayList<BuildStack> stackArray = holder.getStackList();
        //checks the board for internal moves
        for (int i = 0; i < 7; i++) {
            //checking for moves from the stack that can lead to card being inserted into the suit
            checkForMoves(moveLogic.checkStackToSuit(suit, stackArray.get(i), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            for (int j = i + 1; j < 7; j++) {
                if (stackArray.get(j).getStack().size() == 0)
                    continue;
                //Checking for possible moves internally between the stacks
                checkForMoves(moveLogic.checkInternalStackMove(holder, stackArray.get(i), stackArray.get(j), move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
            }
        }

        if (!move.hasMoves()) {
            //checks for deck moves as the last thing
            checkForMoves(moveLogic.findTalonMove(talon, stackArray, suit, move), holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
//            System.out.println("Move combination alternative move" + moveLogic.findAlternativeStackMove(stackArray, move));
            checkForMoves(moveLogic.findAlternativeStackMove(stackArray,move),holder.cloneHolder(), talon.cloneTalon(), suit.cloneSuit());
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

        System.out.println("Move Combination Found: " + move);
        listOfMoves.add(move);
    }

    /** This method simply setup all the stacks with their cards turned and unturned
     * @param cards the List of cards going into the build stacks*/
    public void setUpStacks(ArrayList<Card> cards) {
        for (int i = 0; i <= 6; i++) {
            ArrayList<Block> blocks = new ArrayList<>();
            for (int j = i; j > 0; j--) {
                LinkedList<Card> temp = new LinkedList<>();
                temp.add(new Card(false));
                blocks.add(new Block(temp));
            }

            LinkedList<Card> temp = new LinkedList<>();


            if(cards.get(i).getFaceValue() == 0) {
                /* TODO SÅLEDES AT KONGE RYK KAN TESTES*/

                board.add(new BuildStack());
                board.get(i).setIndex(i);
                buildStackHolder = new BuildStackHolder(board);
            } else {
                temp.add(cards.get(i));
                blocks.add(new Block(temp));
                board.add(new BuildStack(blocks));
                board.get(i).setIndex(i);
                buildStackHolder = new BuildStackHolder(board);
            }

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
