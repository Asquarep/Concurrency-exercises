import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EX18 {
    static class Nontask {
        public static void rest() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch(InterruptedException e) {
                System.out.println("Sleep interrupted");
            } finally {
                System.out.println("Good Bye");
            }
        }
    }

    static class Worker implements Runnable {
        public void run() {
            Nontask.rest();
        }
    }

        public static void main(String[] args) {
            Thread t = new Thread(new Worker());
            t.start();
            t.interrupt();

            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(new Worker());
            exec.shutdownNow();

            ExecutorService exec2 = Executors.newSingleThreadExecutor();
            Future<?> f = exec2.submit(new Worker());
            try {
                TimeUnit.MILLISECONDS.sleep(100); // start task
            } catch(InterruptedException e) {
                System.out.println("Sleep interrupted in main()");
            }
            f.cancel(true);
            exec2.shutdown();
        }

}
