import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class BuildStack {
    private ArrayList<Block> stack;
    private int index;

    public BuildStack() {
        stack = new ArrayList<>();
    }
    public BuildStack(ArrayList<Block> stack) {
        this.stack = stack;
    }
    public BuildStack(BuildStack stack) {
        this.stack = new ArrayList<>();
        for (Block block : stack.getStack()) {
            this.stack.add(new Block(block));
        }
    }

    public BuildStack cloneBuildstack() {
        BuildStack clone = new BuildStack();
        for (int i = 0; i < this.getStack().size(); i++) {
            clone.getStack().add(this.stack.get(i).cloneBlock());
        }
        return clone;
    }

    public ArrayList<Block> getStack() {
        return stack;
    }

    public Block getStackLeader() {
        if (stack.size() == 0)
            return null;
        else
            return stack.get(stack.size()-1);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    public void removeStack(Block block) {
        stack.remove(block);
    }
}
