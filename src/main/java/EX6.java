import java.util.concurrent.*;
import java.util.*;

public class EX6 implements Runnable {
    Random random = new Random();
    public void run() {
        try {
            int t = 1000 * random.nextInt(10);
            TimeUnit.MILLISECONDS.sleep(t);
            System.out.println("Slept " + t/1000 + " seconds");
        } catch(InterruptedException e) {
            System.err.println("Interrupted");
        }

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int in = 0;
        System.out.println("Usage: enter number of tasks");
        in= scanner.nextInt();
        if(in >= 1) {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < in; i++)
                exec.execute(new EX6());
            exec.shutdown();
        }
    }
}
