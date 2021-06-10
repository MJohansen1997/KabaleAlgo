import java.util.LinkedList;

public class Move {
    int point;
    LinkedList<Card> moveList;

    public Move(){
        point = 0;
        moveList = new LinkedList<>();
    }

    public void addMove(int point, Card card1, Card card2) {
        this.point = point;
        moveList.add(card1);
        moveList.add(card2);
    }

    @Override
    public String toString() {
        return "Move{ " + point + "points " + moveList.toString() + '}';
    }
}
