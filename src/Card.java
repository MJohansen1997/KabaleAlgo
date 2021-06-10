import Enums.Colour;
import Enums.Type;

public class Card {
    private boolean faceUp;
    private int faceValue;
    private Colour colour;
    private Type type;


    public Card(boolean faceUp, int faceValue, Type type) {
        this.faceUp = faceUp;
        this.faceValue = faceValue;
        if (4 % (type.getValue() + 1) == 0)
            this.colour = Colour.Red;
        else
            this.colour = Colour.Black;
        this.type = type;
    }

    public Card(boolean faceUp) {
        this.faceUp = faceUp;
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

    @Override
    public String toString() {
        return "Card{" + faceValue + type.getString() + '}';
    }
}
