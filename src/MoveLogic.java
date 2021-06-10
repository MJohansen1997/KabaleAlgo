import java.util.ArrayList;

public class MoveLogic {

    private boolean colourDiff(Card card1, Card card2) {
        return card1.getColour() != card2.getColour();
    }

    private boolean typeDiff(Card card1, Card card2) {
        return card1.getType() != card2.getType();
    }

    private boolean suitCheck(Card moveCard, Suit suit) {
        //gets the top card of the goal of the same type of moveCard
        Card toCard = suit.getTop(moveCard.getType().getValue());
        //Goal is 1 lower than the FrontCard
        return (moveCard.getFaceValue() - toCard.getFaceValue() == 1);
    }

    private boolean stackCheck(Card moveCard, Card toCard) {
        return (toCard.getFaceValue()-moveCard.getFaceValue() == 1);
    }

    private boolean checkLegalMove(Card moveCard, Card toCard) {
        return stackCheck(moveCard, toCard) && colourDiff(moveCard, toCard);
    }

    public Move unTurnedCard(BuildStack stack, Move move) {
            if(!stack.getStackLeader().getLeader().isFaceUp()) {
                return move.addMove(5+1+stack.getStack().size(), null, null);
            }
        return null;
    }

    public Move checkStackToSuit(Suit suit, BuildStack stack, Move move) {
        //gets the stack leaders, docker aka front card
        BuildStack temp = stack;
        Card frontCard = temp.getStackLeader().getDocker();
        if (suitCheck(frontCard, suit)) {
            int type = frontCard.getType().getValue();
            System.out.println("Legal Move Found: " + frontCard + " to Suit");
            if (temp.getStackLeader().getBlock().size() == 1)
                temp.removeStack(stack.getStackLeader());
            else
                temp.getStackLeader().getBlock().remove(frontCard);
            Card suitCard = suit.getSuit(type).peekLast();
            suit.getSuit(type).add(frontCard);
            return move.addMove(10, frontCard, suitCard);
        }
        return null;
    }

    public Move checkInternalMove(BuildStack stack1, BuildStack stack2, Move move) {
        Block block1 = stack1.getStackLeader();
        Block block2 = stack2.getStackLeader();
        Card block1Leader = block1.getLeader();
        Card block1Docker = block1.getDocker();
        Card block2Leader = block2.getLeader();
        Card block2Docker = block2.getDocker();

        if (checkLegalMove(block1Leader, block2Docker)) {
            //legal move
            System.out.println("Legal Move Found: " + block1Leader + " to " + block2Docker);
            block2.getBlock().addAll(block1.getBlock());
            stack1.removeStack(block1);
            return move.addMove(5, block1Leader, block2Docker);
        }
        else if (checkLegalMove(block2Leader, block1Docker)) {
            //legal move
            System.out.println("Legal Move Found: " + block2Leader + block1Docker);
            block1.getBlock().addAll(block2.getBlock());
            stack2.removeStack(block2);
            return move.addMove(5, block2Leader, block1Docker);
        }
        return null;
    }

    public Move findTalonMove(int index, Talon talon, ArrayList<BuildStack> stacks, Suit suit, Move move) {
        Card deckCard = talon.getDeck().get(index);
        if (suitCheck(deckCard, suit)) {
            int typeInt = deckCard.getType().getValue();
            Card temp = suit.getTop(typeInt);
            //Card can be moved into goal
            System.out.println("Legal Move Found: From Talon " + deckCard + " to Suit");
            suit.getSuit(typeInt).add(deckCard);
            return move.addMove(10, deckCard, temp);
        }
        //checks for each buildstack if the card can be added to that stacks docker
        for (BuildStack stack : stacks) {
            Card stackDocker = stack.getStackLeader().getDocker();
            if (checkLegalMove(deckCard, stackDocker)) {
                //card can be moved to the stack
                System.out.println("Legal Move Found: From Talon " + deckCard + " To " + stackDocker);
                stack.getStackLeader().getBlock().add(deckCard);
                talon.getDeck().remove(deckCard);
                return move.addMove(5, deckCard, stackDocker);
            }
        }
        return null;
    }
}
