import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class EX30 {
    static class Sender implements Runnable {
        private Random rand = new Random(47);
        private LinkedBlockingQueue<Character> queue;
        public Sender(LinkedBlockingQueue<Character> lbq) {
            queue = lbq;
        }
        public void run() {
            try {
                while(true)
                    for(char c = 'A'; c <= 'z'; c++) {
                        queue.put(c);
                        TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                    }
            } catch(InterruptedException e) {
                System.out.println(e + " Sender sleep interrupted");
            }
        }
    }

    static class Receiver implements Runnable {
        private LinkedBlockingQueue<Character> queue;
        public Receiver(LinkedBlockingQueue<Character> lbq) {
            queue = lbq;
        }
        public void run() {
            try {
                while(true) {
                    System.out.println("Read: " + (char)queue.take() + ", ");
                }
            } catch(InterruptedException e) {
                System.out.println(e + " Receiver read exception");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<Character> lbq = new LinkedBlockingQueue<Character>();
        Sender sender = new Sender(lbq);
        Receiver receiver = new Receiver(lbq);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(6);
        exec.shutdownNow();
    }
}
