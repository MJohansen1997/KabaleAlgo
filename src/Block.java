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
        for (int i = 0; i < this.getBlock().size(); i++) {
            temp.block.add(this.getBlock().get(i).cloneCard());
        }
        return temp;
    }

    public int blockContains(Card card) {
        for (int i = 0; i < block.size(); i++) {
            Card comparer = block.get(i);
            if (comparer.compareCards(card))
                return i;
        }
        return -1;
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
