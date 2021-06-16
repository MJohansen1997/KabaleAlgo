import java.util.ArrayList;
import java.util.LinkedList;
/** The move class is used to contain a chain of moves and their total amount of algorithmic points*/
public class Move {
    int point;
    LinkedList<Card> moveList;
    LinkedList<Card> moveListSim;

    public Move() {
        this.point = 0;
        this.moveList = new LinkedList<>();
        this.moveListSim = new LinkedList<>();
    }

    public Move addMove(int points, Card card1, Card card2) {
        Move move = new Move();
        move.point = point + points;

        move.moveList.addAll(moveList);
        move.moveList.add(card1);
        move.moveList.add(card2);

        move.moveListSim.addAll(moveListSim);
        move.moveListSim.add(card1);
        move.moveListSim.add(card2);

        return move ;
    }

    public void add(Move move) {
        this.point += move.point;
        this.moveList.addAll(move.moveList);
        this.moveListSim.addAll(move.moveListSim);
    }

    public boolean hasMoves() {
        return moveListSim.size() >= 2;
    }

    public LinkedList<Card> getSimMoves() {
        return moveListSim;
    }

    @Override
    public String toString() {
        return "Move{ " + point + " points " + moveList.toString() + '}';
    }
}
