import java.util.LinkedList;

public class Move {
    int point;
    LinkedList<Card> moveList;

    public void addMove(Card card1, Card card2) {
        moveList.add(card1);
        moveList.add(card2);
    }
}
