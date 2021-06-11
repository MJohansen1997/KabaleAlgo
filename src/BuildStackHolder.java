import java.util.ArrayList;
import java.util.Stack;

public class BuildStackHolder {
    ArrayList<BuildStack> stacks;

    public BuildStackHolder() {
        stacks = new ArrayList<>();
    }

    public BuildStackHolder(ArrayList<BuildStack> arrayList) {
        stacks = arrayList;
    }

    public BuildStackHolder(BuildStackHolder buildStackHolder) {
        stacks = new ArrayList<>();
        for (BuildStack stack : buildStackHolder.stacks) {
            stacks.add(new BuildStack(stack));
        }
    }

    public BuildStackHolder cloneHolder() {
        BuildStackHolder temp = new BuildStackHolder();
        for (int i = 0; i < this.stacks.size(); i++) {
            temp.stacks.add(this.stacks.get(i).cloneBuildstack());
        }
        return temp;
    }
}
