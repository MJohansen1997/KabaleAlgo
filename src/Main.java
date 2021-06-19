import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Logic logic = new Logic();
//        logic.setUp();
//        logic.run();
        testAlgorithm(logic, 20, true);
    }

    public static void testAlgorithm(Logic logic, int runs, boolean setValues) {
        ArrayList<Integer> setValueWins = new ArrayList<>();
        for (int i = 0; i <= runs; i++) {
//            logic.generateGame(setValues, i);
            System.out.println("\nTEST NUMBER : "+i+"\n");
            logic = new Logic();
            logic.generateGame(setValues,i);
            logic.run();
            if(logic.winnable)
                setValueWins.add(i);

        }
        System.out.println("The algorithm solved the games with the set values of: " + setValueWins);
    }
}
