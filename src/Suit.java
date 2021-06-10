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

    public Suit(Suit suitOld) {

    }

    public ArrayList<LinkedList<Card>> getSuitArray() {
        return suitArray;
    }

    public Card getTop(int index) {
        return suitArray.get(index).peekLast();
    }

    public Suit addSuit(int index, Card card) {
        Suit newSuit = new Suit();
        newSuit.suitArray = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LinkedList<Card> temp = new LinkedList<>(suitArray.get(i));
            newSuit.suitArray.add(temp);
        }
        newSuit.getSuit(index).add(card);
        return newSuit;
    }

    public LinkedList<Card> getSuit(int typeIndex) {
        return suitArray.get(typeIndex);
    }
}
