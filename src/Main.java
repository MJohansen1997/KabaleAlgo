import Enums.Colour;
import Enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Logic logic = new Logic();
        logic.setUp();
        //while(!won || no Moves) {
        logic.checkForMoves();
        //logic.performMoves();
        //  UpdateUI - Call for Activity To Do Move
        //logic.ControllFaceDown();
        //}
    }
}
