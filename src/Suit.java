import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Suit {
    ArrayList<LinkedList<Card>> suitArray;

    public Suit() {
        suitArray = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LinkedList<Card> temp = new LinkedList<>();
            temp.add(new Card(true, 0, Type.fromInteger(i) ));
            suitArray.add(temp);
        }
    }

    public Card getTop(int index) {
        return suitArray.get(index).peekLast();
    }

    public LinkedList<Card> getSuit(int typeIndex) {
        return suitArray.get(typeIndex);
    }
}
