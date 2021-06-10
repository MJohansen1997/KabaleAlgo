import java.util.ArrayList;
import java.util.LinkedList;

public class Suit {
    ArrayList<LinkedList<Card>> goal;

    public Suit() {
        goal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            goal.add(new LinkedList<Card>());
        }
    }

    public ArrayList<LinkedList<Card>> getGoal() {
        return goal;
    }

    public Card getTop(int index) {
        return goal.get(index).peekLast();
    }
}
