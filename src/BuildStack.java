import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**The Buildstack acts as an container for our blocks, which provides methods to access the blocks */
public class BuildStack {
    private final ArrayList<Block> stack;
    private int index;

    public BuildStack() {
        stack = new ArrayList<>();
    }
    public BuildStack(ArrayList<Block> stack) {
        this.stack = stack;
    }

    /**This method is used to clone the buildstack in order to get a new object of another object for the recursion */
    public BuildStack cloneBuildstack() {
        BuildStack clone = new BuildStack();
        clone.setIndex(this.getIndex());
        for (int i = 0; i < this.getStack().size(); i++) {
            clone.getStack().add(this.stack.get(i).cloneBlock());
        }
        return clone;
    }

    public ArrayList<Block> getStack() {
        return stack;
    }

    /** the stack leader is basically the block that is in front and moveable this method returns the block who is
     * in front in the stack*/
    public Block getStackLeader() {
        if (stack.size() == 0)
            return null;
        else
            return stack.get(stack.size()-1);
    }
    //Returns the index of the stack
    public int getIndex() {
        return index;
    }

    /** Sets the value of index
     * @param  index The value you want index to be set to*/
    public void setIndex(int index) {
        this.index = index;
    }

    /** This method is used to remove a card from a stack
     * @param card is the card you want to remove from the stack*/
    public boolean removeCard(Card card) {
        //this stack contains card1
        int index = this.getStackLeader().blockContains(card);
        if (index == 0) {
            this.getStack().remove(this.getStackLeader());
            return true;
        }
        else if (index == this.getStack().size()-1) {
            this.getStackLeader().getBlock().remove(card);
            return true;
        }
        else if (index != -1) {
            //if the card isn't in leader position or docker position
            //then we need to move multiple cards.
            return true;
        }
        else
            return false;
    }
}
