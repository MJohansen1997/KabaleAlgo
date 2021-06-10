import java.util.ArrayList;
import java.util.Stack;

public class BuildStackHolder {
    ArrayList<BuildStack> stacks;

    public BuildStackHolder(ArrayList<BuildStack> arrayList) {
        stacks = arrayList;
    }

    public BuildStackHolder(BuildStackHolder buildStackHolder) {
        stacks = new ArrayList<>();
        for (BuildStack stack : buildStackHolder.stacks) {
            stacks.add(new BuildStack(stack));
        }
    }
}
