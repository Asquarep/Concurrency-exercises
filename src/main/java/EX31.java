import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class EX31 {
    static class Chopstick{}

    public static class Philosopher implements Runnable {
        private Chopstick left;
        private Chopstick right;
        private LinkedBlockingQueue<Chopstick> bin;
        private final int id;
        private final int ponderFactor;
        private Random rand = new Random(47);
        private void pause() throws InterruptedException {
            if(ponderFactor == 0) return;
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
        }
        public Philosopher(Chopstick left, Chopstick right,
                             LinkedBlockingQueue<Chopstick> bin, int ident, int ponder) {
            this.left = left;
            this.right = right;
            this.bin = bin;
            id = ident;
            ponderFactor = ponder;
        }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    System.out.println(this + " " + "thinking");
                    pause();
                    // Philosopher becomes hungry
                    System.out.println(this + " taking first, right chopstick");
                    right = bin.take();
                    System.out.println(this + " taking second, left chopstick");
                    left = bin.take();
                    System.out.println(this + " eating");
                    pause();
                    System.out.println(this + " returning chopsticks");
                    bin.put(right);
                    bin.put(left);
                }
            } catch(InterruptedException e) {
                System.out.println(this + " " + "exiting via interrupt");
            }
        }
        public String toString() { return "Philosopher " + id; }
    }

    public static class DeadlockingDiningPhilosophers {
        public static void main(String[] args) throws Exception {
            int ponder = 5;
            if(args.length > 0)
                ponder = Integer.parseInt(args[0]);
            int size = 5;
            if(args.length > 1)
                size = Integer.parseInt(args[1]);
            ExecutorService exec = Executors.newCachedThreadPool();
            // chopstick bin:
            LinkedBlockingQueue<Chopstick> bin = new LinkedBlockingQueue<Chopstick>();
            Chopstick[] sticks = new Chopstick[size];
            for(int i = 0; i < size; i++) {
                sticks[i] = new Chopstick();
                bin.put(sticks[i]);
            }
            for(int i = 0; i < size; i++)
                exec.execute(new Philosopher(sticks[i], sticks[(i + 1) % size], bin, i, ponder));
            if(args.length == 3 && args[2].equals("timeout"))
                TimeUnit.SECONDS.sleep(5);
            else {
                System.out.println("Press 'Enter' to quit");
                System.in.read();
            }
            exec.shutdownNow();
        }
    }
}
