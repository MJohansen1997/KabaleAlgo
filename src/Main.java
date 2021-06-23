import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Logic logic = new Logic();
//        logic.setUp();
//        logic.run();
        testAlgorithmThreads(logic, 100, true);
    }
    
    public static void testAlgorithmThreads(Logic logic, int runs, boolean setValues) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        final ArrayList<Integer>[] setValueWins = new ArrayList[]{new ArrayList<>()};
        ArrayList<Double> totalTime = new ArrayList<>();
        totalTime.add((double) 0);
        ArrayList<Thread> threadList = new ArrayList<>();
        //final long[] totalTime = {0};
        for (int j = 0; j < 10; j++) {
            int finalJ = j;
            Thread thread = new Thread(String.valueOf(j)) {
                Logic logic = new Logic();
                int i;

                public void run() {
                    while (counter.getCount() < runs) {
                        synchronized (this) {
                            i = counter.increment();
                        }
                        System.out.println("Thread" + this.getName() + " is starting on i = " + i);
                        logic = new Logic();
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
        System.out.println("The algorithm solved the games with the set values of: " + setValueWins[0] + " which means that " + setValueWins[0].size() + " games got solved out of " + runs + " and it took an average time of " + (totalTime.get(0) / setValueWins[0].size()) / 1000 + " seconds to solve them");
    }

    static class SynchronizedCounter {
        private int count = 0;

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
