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

    public Suit cloneSuit() {
        Suit temp = new Suit();
        temp.suitArray = new ArrayList<>();
        for (int i = 0; i < this.suitArray.size(); i++) {
            temp.suitArray.add(new LinkedList<>());
            for (int j = 0; j < this.getSuit(i).size(); j++) {
                temp.getSuit(i).add(this.getSuit(i).get(j));
            }
        }
        return temp;
    }

    public Card getTop(int index) {
        if (index < 0 || index > 3)
            return new Card(false);
        return suitArray.get(index).peekLast();
    }

    public LinkedList<Card> getSuit(int index) {
        if (index < 0 || index > 3)
            return new LinkedList<>();
        return suitArray.get(index);
    }

    public boolean removeCard(Card card) {
        LinkedList<Card> list = getSuit(card.getType().getValue());
        for (int i = 0; i < list.size(); i++) {
            Card suitCard =list.get(i);
            if (suitCard.compareCards(card)) {
                list.remove(i);
                return true;
            }
        }
        return false;
    }
}
