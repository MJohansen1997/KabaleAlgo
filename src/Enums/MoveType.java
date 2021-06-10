package Enums;

public enum MoveType {
    stackToGoal(10),
    deckToStack(5),
    turnCard(5),
    goalToStack(-15),
    emptyGarbage(100),
    kingNoQueen(-1);


    private final int value;

    MoveType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
