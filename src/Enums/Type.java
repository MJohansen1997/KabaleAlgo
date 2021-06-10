package Enums;

public enum Type {
    Clover(0, "♣️"),
    Spade(2, "♠️"),
    Heart(1, "♥️"),
    Diamond(3, "♦️");

    private final int value;
    private final String string;

    Type(final int newValue, final String newString) {
        value = newValue;
        string = newString;
    }

    public int getValue() { return value; }
    public String getString() { return string; }
}
