import java.util.ArrayList;

public class MoveLogic {

    private boolean colourDiff(Card card1, Card card2) {
        return card1.getColour() != card2.getColour();
    }

    private boolean typeDiff(Card card1, Card card2) {
        return card1.getType() != card2.getType();
    }

    private boolean goalCheck(Card moveCard, Suit suit) {
        //gets the top card of the goal of the same type of moveCard
        Card toCard = suit.getTop(moveCard.getType().getValue());

        if (toCard == null) {
            if (moveCard.getFaceValue() == 1) {
                //Goal is empty and there is an ace
                return true;            }
            else
                return false;
        }
        else if (moveCard.getFaceValue() - toCard.getFaceValue() == 1) {
            //Goal is 1 lower than the FrontCard
            return true;
        }
        else
            return false;
    }

    private boolean stackCheck(Card moveCard, Card toCard) {
        return (toCard.getFaceValue()-moveCard.getFaceValue() == 1);
    }

    private boolean checkLegalMove(Card moveCard, Card toCard) {
        return stackCheck(moveCard, toCard) && colourDiff(moveCard, toCard);
    }

    public Move checkStackToGoal(Suit suit, BuildStack stack) {
        //gets the stack leaders, docker aka front card
        Card frontCard = stack.getStackLeader().getDocker();

        if (goalCheck(frontCard, suit))
            System.out.println("Legal Move Found: " + frontCard + " to Suit");

        return null;
    }

    public Move checkInternalMove(BuildStack stack1, BuildStack stack2) {
        Block block1 = stack1.getStackLeader();
        Block block2 = stack2.getStackLeader();
        Card block1Leader = block1.getLeader();
        Card block1Docker = block1.getDocker();
        Card block2Leader = block2.getLeader();
        Card block2Docker = block2.getDocker();

        if (checkLegalMove(block1Leader, block2Docker)) {
            //legal move
            System.out.println("Legal Move Found: " + block1Leader + " to " + block2Docker);
        }
        else if (checkLegalMove(block2Leader, block1Docker)) {
            //legal move
            System.out.println("Legal Move Found: " + block2Leader + block1Docker);
        }
        return null;
    }

    public Move findDeckMove(Talon talon, ArrayList<BuildStack> stacks, Suit suit) {
        for (Card deckCard : talon.getDeck()) {
            if (goalCheck(deckCard, suit)) {
                //Card can be moved into goal
                System.out.println("Legal Move Found: From Talon " + deckCard + " to Suit");
            }
            //checks for each buildstack if the card can be added to that stacks docker
            for (BuildStack stack : stacks) {
                Card stackDocker = stack.getStackLeader().getDocker();
                if (checkLegalMove(deckCard, stackDocker)) {
                    //card can be moved to the stack
                    System.out.println("Legal Move Found: From Talon " + deckCard+ " To " + stackDocker);
                }
            }
        }
        return null;
    }
}
