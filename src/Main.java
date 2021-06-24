import javafx.concurrent.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
/**
 * @author Gruppe 13 - Jacob Christensen s174130, Mads Hansen s195456, Mikkel Johansen s175194, Shania Hau s195477, Stefan Luxhøj s195467
 **/
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Logic logic = new Logic();
//        logic.setUp();
//        logic.run();

        for (int i = 1; i < 3;) {
            System.out.println("STARTING TEST FOR DEPTH: " + i);
            if(i == 1)
                if(testAlgorithmThreads(logic, 1010, i, true))
                    i++;
            if(i == 2)
                if(testAlgorithmThreads(logic, 110, i, true))
                    i++;
        }
    }

    public static boolean testAlgorithmThreads(Logic logic, int runs, int depth, boolean setValues) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        final ArrayList<Integer>[] setValueWins = new ArrayList[]{new ArrayList<>()};
        ArrayList<Double> totalTime = new ArrayList<>();
        totalTime.add((double) 0);
        ArrayList<Thread> threadList = new ArrayList<>();
        //final long[] totalTime = {0};
        for (int j = 0; j < 8; j++) {
            int finalJ = j;

            Thread thread = new Thread(String.valueOf(j)) {
                Logic logic;
                int i;

                public void run() {
                    while (counter.getCount() < runs) {
                        synchronized (this) {
                            i = counter.increment();
                        }
                        System.out.println("Thread" + this.getName() + " is starting on i = " + i);
                        logic = new Logic();
                        logic.setDepth(depth);
                        logic.generateGame(setValues, i);
                        long time1 = System.currentTimeMillis();
                        logic.run();
                        if (logic.winnable) {
                            synchronized (this) {
                                System.out.println("Thread" + this.getName() + " is winning");
                                setValueWins[0].add(i);
//                            System.out.println("has won " + setValueWins.size() + " out of " + i);
                                long time2 = System.currentTimeMillis();
                                totalTime.set(0, totalTime.get(0) + (time2 - time1));
                            }
                        }
                        System.out.println("Thread" + this.getName() + " is finishing on i = " + i);
                    }
                }
            };
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        double procent = ((double)setValueWins[0].size()/(runs-10))*100;
        String result = "The algorithm solved the games with the set values of: " + setValueWins[0] + " which means that " + setValueWins[0].size() + " games got solved out of " + (runs-10) + " which equals a solve rate of " + procent + "%" + " and it took an average time of " + (totalTime.get(0) / setValueWins[0].size()) / 1000 + " seconds to solve them\n";
        writeResult(result, depth);
        System.out.println(result);
        return true;
    }


    public static void writeResult(String result, int depth) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
            Date date = new Date();

            File file = new File("C:\\Users\\2100m\\Desktop\\Test results of 7-Solitaire algorithm\\Results.txt");
            if(!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("### " +depth+ " DEPTH TEST    :   " + formatter.format(date) + " ###\n");
            bw.write(result);
            bw.newLine();
            bw.close();
            System.out.println("WROTE TO FILE");
        } catch (IOException e ) {
            System.out.println("Error occurred. Couldn't LOG");
            e.printStackTrace();
        }
    }

    static class SynchronizedCounter {
        /* skipping test 8 */
        private int count = 10;

        // Synchronized Method
        public synchronized int increment() {
            count = count + 1;
            return count;
        }

        public int getCount() {
            return count;
        }
    }
}
