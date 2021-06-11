package Enums;

public enum Type {
    Clover(0, "♣️"),
    Spade(2, "♠️"),
    Heart(1, "♥️"),
    Diamond(3, "♦️"),
    Unturned(-1,"?");

    private final int value;
    private final String string;

    Type(final int newValue, final String newString) {
        value = newValue;
        string = newString;
    }

    //method uses to get an type from an integer
    public static Type fromInteger(int x) {
        switch(x) {
            case 0:
                return Clover;
            case 1:
                return Heart;
            case 2:
                return Spade;
            case 3:
                return Diamond;
            default:
                return Unturned;
        }
    }

    //methods to get either the int value or the string value of an Type enum
    public int getValue() { return value; }
    public String getString() { return string; }
}
