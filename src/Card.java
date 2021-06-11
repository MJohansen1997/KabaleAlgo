import Enums.Colour;
import Enums.Type;

public class Card {
    private boolean faceUp;
    private int faceValue;
    private Colour colour;
    private Type type;

    public Card() {

    }
    public Card(boolean faceUp, int faceValue, Type type) {
        this.faceUp = faceUp;
        this.faceValue = faceValue;
        if (15 % (type.getValue() + 2) == 0)
            this.colour = Colour.Red;
        else
            this.colour = Colour.Black;
        this.type = type;
    }

    public Card(boolean faceUp) {
        this.faceUp = faceUp;
        this.faceValue = 0;
        this.type = Type.Unturned;
    }

    public Card cloneCard() {
        Card temp = new Card(false);
        temp.type = this.type;
        temp.faceValue = this.faceValue;
        temp.colour = this.colour;
        temp.faceUp = this.faceUp;
        return temp;
    }

    public Colour getColour() {
        return colour;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public Type getType() {
        return type;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp() {
        faceUp = true;
    }

    public boolean compareCards(Card toCompare) {
        return this.getFaceValue() == toCompare.getFaceValue() && this.getType() == toCompare.getType();
    }

    @Override
    public String toString() {
        return "Card{" + faceValue + type.getString() + '}';
    }
}
