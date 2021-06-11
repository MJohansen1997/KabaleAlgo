import java.util.LinkedList;

public class Move {
    int point;
    LinkedList<Card> moveList;
//    LinkedList<Card> moveListSim;

    public Move() {
        point = 0;
        moveList = new LinkedList<>();
//        moveListSim = new LinkedList<>();
    }
    public Move(int point, LinkedList<Card> list) {
        this.point = point;
        moveList = new LinkedList<>();
//        moveListSim = new LinkedList<>();
    }

    public Move(Move move) {
        point =+ move.point;
        moveList = new LinkedList<>();
        while (move.moveList.peekFirst() != null) {
            moveList.add(move.moveList.pollFirst());
        }
//        while (move.moveListSim.peekFirst() != null) {
//            moveListSim.add(move.moveListSim.pollFirst());
//        }
    }

    public Move addMove(int points, Card card1, Card card2) {
        Move move = new Move();
        move.moveList.addAll(moveList);
        if (card1 != null) {
            move.point = point + points;
            move.moveList.add(card1);
            move.moveList.add(card2);
        }
//        move.moveListSim.addAll(moveListSim);
//        moveListSim.add(card1);
//        moveListSim.add(card2);
        return move ;
    }

    public boolean hasMoves() {
        return moveList.size() >= 2;
    }

    public LinkedList<Card> getMove() {
        LinkedList<Card> temp = new LinkedList<>();
        temp.add(moveList.pollFirst());
        temp.add(moveList.pollFirst());
        return temp;
    }

//    public LinkedList<Card> getSim() {
//        LinkedList<Card> temp = new LinkedList<>();
//        temp.add(moveListSim.pollFirst());
//        temp.add(moveListSim.pollFirst());
//        return temp;
//    }

    @Override
    public String toString() {
        return "Move{ " + point + " points " + moveList.toString() + '}';
    }
}
