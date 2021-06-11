import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class BuildStack {
    private ArrayList<Block> stack;

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
        return stack.get(stack.size()-1);
    }

    public void removeStack(Block block) {
        stack.remove(block);
    }
}
