import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class BuildStack {
    ArrayList<Block> stack;

    public BuildStack(ArrayList<Block> stack) {
        this.stack = stack;
    }

    public ArrayList<Block> getStack() {
        return stack;
    }

    public Block getStackLeader() {
        return stack.get(stack.size()-1);
    }
}
