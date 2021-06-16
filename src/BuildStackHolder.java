import java.util.ArrayList;

public class BuildStackHolder {
    private ArrayList<BuildStack> stacks;

    public BuildStackHolder() {
        stacks = new ArrayList<>();
    }

    public BuildStackHolder(ArrayList<BuildStack> arrayList) {
        stacks = arrayList;
    }

    public BuildStackHolder cloneHolder() {
        BuildStackHolder temp = new BuildStackHolder();
        for (int i = 0; i < this.stacks.size(); i++) {
            temp.stacks.add(this.stacks.get(i).cloneBuildstack());
        }
        return temp;
    }

    public boolean removeBlock(Card card){
        for(BuildStack stack : stacks) {
            if(!(stack.getStackLeader().removeBlock(card) == null))
                return true;
        }
        return false;
    }

    public boolean removeCard(Card card){
        for (BuildStack stack : stacks) {
            if (stack.removeCard(card))
                return true;
        }
        return false;
    }

    public ArrayList<BuildStack> getStackList() {
        return stacks;
    }
}
