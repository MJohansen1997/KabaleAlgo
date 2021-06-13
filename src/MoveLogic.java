import Enums.Type;

import java.util.ArrayList;

//this class contain all the logic of how cards can be moved and the scoring of the moves
public class MoveLogic {
    /** This method checks if two cards have different colours
     * @param card1 First card to compare
     * @param card2 second card to compare
     * Returns true if they have different colours and False if they got the same colour*/
    private boolean colourDiff(Card card1, Card card2) {
        return card1.getColour() != card2.getColour();
    }

    /** This method checks if a card can be moved into it's suit*/
    private boolean suitCheck(Card moveCard, Suit suit) {
        //gets the top card of the suit that contains that cards type
        Card toCard = suit.getTop(moveCard.getType().getValue());
        //returns True if the card that we want to add to the suit is one lower than the card already in
        return (moveCard.getFaceValue() - toCard.getFaceValue() == 1);
    }

    /**The stackCheck method checks if a card is stackable with another card which would mean they only have a value
     * difference of 1
     * @param moveCard  The card we want to move
     * @param toCard  The card which we want to move onto*/
    private boolean stackCheck(Card moveCard, Card toCard) {
        return (toCard.getFaceValue() - moveCard.getFaceValue() == 1);
    }

    /** This method checks if moving a card to a certain other card is a legal move
     * @param moveCard The card we want to move
     * @param toCard The card we want to move the other card onto*/
    private boolean checkLegalMove(Card moveCard, Card toCard) {
        //checks that neither of the cards is of type unturned which for the algorithm means it's a unknown card
        if (moveCard.getType() == Type.Unturned || toCard.getType() == Type.Unturned)
            return false;
        //returns the result of stackCheck and colourDiff
        return stackCheck(moveCard, toCard) && colourDiff(moveCard, toCard);
    }

    /** This method checks if a stack contains an unturned card
     * @param stack The stack that we searches through
     * @param move The Move object we want the possible turn move inserted to*/
    public Move unTurnedCard(BuildStack stack, Move move) {
        //checks if the front card aka leader of a stacks leader block is a unturned card
        if (!stack.getStackLeader().getLeader().isFaceUp()) {
//                System.out.println("Unturned card found at: " + stack.getIndex() + " : " + stack.getStackLeader().block.indexOf(stack.getStackLeader().getLeader()));
            return move.addMove(1+(stack.getStack().size()), stack.getStackLeader().getLeader(), stack.getStackLeader().getLeader());
        }
        return move;
    }

    /** This method searches for a possible card which could be added to the suit*/
    public Move checkStackToSuit(Suit suit, BuildStack stack, Move move) {
        //gets the stack leaders, docker aka front card
        Card frontCard = stack.getStackLeader().getDocker();
        if (suitCheck(frontCard, suit)) {
            int type = frontCard.getType().getValue();
//            System.out.println("Legal Move Found: " + frontCard + " to Suit");
            return move.addMove(0, frontCard, suit.getTop(type));
        }
        return null;
    }

    /** This method searches for internal moves of cards between build stacks on the board*/
    public Move checkInternalStackMove(BuildStack stack1, BuildStack stack2, Move move) {
        Block block1 = stack1.getStackLeader();
        Block block2 = stack2.getStackLeader();
        Card block1Leader = block1.getLeader();
        Card block1Docker = block1.getDocker();
        Card block2Leader = block2.getLeader();
        Card block2Docker = block2.getDocker();

        //checks if the leader and docker of the two blocks are a legal move
        if (checkLegalMove(block1Leader, block2Docker)) {
            //legal move was found between block1leader and block2docker which means block1 can be added too block2
            int size = stack1.getStack().size();
//            System.out.println("Legal Move Found: " + block1Leader + " to " + block2Docker);
            //here we check if the block is the last one in the stack
            if (size != 1) {
                return move.addMove(1, block1Leader, block2Docker);
            } else {
                return move.addMove(100, block1Leader, block2Docker);
            }
        } else if (checkLegalMove(block2Leader, block1Docker)) {
            //legal move was found between block2leader and block1docker which means block2 can be added too block1
            int size = stack2.getStack().size();
//            System.out.println("Legal Move Found: " + block2Leader + block1Docker);
            //here we check if the block is the last one in the stack
            if (size != 1) {
                return move.addMove(1, block2Leader, block1Docker);
            } else {
                return move.addMove(100, block2Leader, block1Docker);
            }
        }
        return null;
    }

    /** This method looks to find a move which can be made from the talon to either a suit or a build stack*/
    public Move findTalonMove(Talon talon, ArrayList<BuildStack> stacks, Suit suit, Move move) {
//        Card deckCard = talon.getDeck().get(index);
        for (Card deckCard : talon.getDeck()) {


            //checks if the deck card can be added to the suit
            if (suitCheck(deckCard, suit)) {
                int typeInt = deckCard.getType().getValue();
                Card temp = suit.getTop(typeInt);
                //Card can be moved into goal
//            System.out.println("Legal Move Found: From Talon " + deckCard + " to Suit");
                return move.addMove(0, deckCard, temp);
            }
            //checks through each build stack if the card can be added to that stacks docker
            for (BuildStack stack : stacks) {
                Card stackDocker = stack.getStackLeader().getDocker();
                //checks if the two card is a legal move
                if (checkLegalMove(deckCard, stackDocker)) {
                    //card can be moved to the stack
//                System.out.println("Legal Move Found: From Talon " + deckCard + " To " + stackDocker);
                    return move.addMove(1, deckCard, stackDocker);
                }
            }
        }
        return null;
    }

    public boolean matchingQueenToKing (Card king, Card cardTo) {

        return (king.getFaceValue() - cardTo.getFaceValue() == 1);
    }
}
