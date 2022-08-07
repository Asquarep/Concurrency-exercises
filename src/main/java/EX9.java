import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.lang.Thread.*;

public class EX9 implements Runnable {
    private int countDown = 5;
    private volatile double d;
    public String toString() {
        return Thread.currentThread() + ": " + countDown;
    }
    public void run() {
        while(true) {
            for(int i = 0; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double)i;
                if(i % 1000 == 0)
                    Thread.yield();
            }
            System.out.println(this);
            if(--countDown == 0) return;
        }
    }

    static class SimplePriorities9ThreadFactory implements ThreadFactory {
        Random rand = new Random();
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            int i = rand.nextInt(3);
            switch(i) {
                case 0 : t.setPriority(MIN_PRIORITY); break;
                case 1 : t.setPriority(NORM_PRIORITY); break;
                case 2 : t.setPriority(MAX_PRIORITY); break;
                default:
            }
            return t;
        }
    }
    public static void main(String[] args) throws Exception {
        ExecutorService exec =
                Executors.newCachedThreadPool(new SimplePriorities9ThreadFactory());
        for(int i = 0; i < 5; i++)
            exec.execute(new EX9());
        exec.execute(new EX9());
        exec.shutdown();

    }



}
