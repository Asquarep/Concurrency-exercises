import java.util.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EX32 {
    static class Count {
        private int count = 0;
        private Random rand = new Random(47);
        public synchronized int increment() {
            int temp = count;
            if(rand.nextBoolean())
                Thread.yield();
            return (count = ++temp);
        }
        public synchronized int value() { return count; }
    }

    static class Entrance implements Runnable {
        private static Count count = new Count();
        private static List<Entrance> entrances = new ArrayList<Entrance>();
        private int number = 0;
        private final int id;
        private final CountDownLatch doneSignal;
        private static CountDownLatch stopSignal;
        public Entrance(int id, CountDownLatch doneSignal, CountDownLatch stopSignal) {
            this.id = id;
            this.doneSignal = doneSignal;
            this.stopSignal = stopSignal;
            // Keep this task in a list. Also prevents
            // garbage collection of dead tasks:
            entrances.add(this);
        }
        public void run() {
            while(!(stopSignal.getCount() == 0)) {
                synchronized(this) {
                    ++number;
                }
                System.out.println(this + " Total: " + count.increment());
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch(InterruptedException e) {
                    System.out.println("sleep interrupted");
                }
            }
            System.out.println("Closing " + this);
            doneSignal.countDown();
        }
        public synchronized int getValue() { return number; }
        public String toString() {
            return "Entrance " + id + ": " + getValue();
        }
        public static int getTotalCount() {
            return count.value();
        }
        public static int sumEntrances() {
            int sum = 0;
            for(Entrance entrance : entrances)
                sum += entrance.getValue();
            return sum;
        }
    }

    public static void main(String[] args) throws Exception {
        int SIZE = 5;
        CountDownLatch stopSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < SIZE; i++)
            exec.execute(new Entrance(i, doneSignal, stopSignal));
        TimeUnit.SECONDS.sleep(2);
        stopSignal.countDown();
        doneSignal.await();
        exec.shutdown();
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}
