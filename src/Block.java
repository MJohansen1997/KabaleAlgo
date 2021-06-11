import java.util.LinkedList;

public class Block {
    LinkedList<Card> block;

    public Block(LinkedList<Card> block) {
        this.block = block;
    }
    public Block() {
        LinkedList<Card> temp = new LinkedList<>();
        this.block = temp;
    }

    public Block(Block block) {
        this.block = new LinkedList<>(block.block);
    }

    public Block cloneBlock() {
        Block temp = new Block();
        temp.getBlock().addAll(this.getBlock());
        return temp;
    }

    public LinkedList<Card> getBlock() {
        return block;
    }

    public Card getLeader() {
        return block.peekFirst();
    }

    public Card getDocker() {
        return block.peekLast();
    }
}
