import java.util.ArrayList;

public class Talon {
    ArrayList<Card> deck;

    public Talon(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Talon cloneTalon() {
        ArrayList<Card> temp = new ArrayList<>();
        temp.addAll(this.deck);
        return new Talon(temp);
    }

}
