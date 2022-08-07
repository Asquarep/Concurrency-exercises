import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EX3 {
    static class FirstRunable implements Runnable {
        public FirstRunable() {
            System.out.println("Starting FirstRunable");
        }
        public void run() {
            for(int i = 0; i < 3; i++) {
                System.out.println("This is FirstRunable");
                Thread.yield();
            }
            System.out.println("FirstRunable task complete.");
        }
    }

    static class SecondRunable implements Runnable {
        public SecondRunable() {
            System.out.println("Starting SecondRunable");
        }
        public void run() {
            for(int i = 0; i < 3; i++) {
                System.out.println("This is SecondRunnable");
                Thread.yield();
            }
            System.out.println("SecondRunnable task complete.");
        }
    }

    static class ThirdRunable implements Runnable {
        public ThirdRunable() {
            System.out.println("Starting ThirdRunnable");
        }
        public void run() {
            for(int i = 0; i < 3; i++) {
                System.out.println("This is ThirdRunnable");
                Thread.yield();
            }
            System.out.println("ThirdRunnable task complete.");
        }
    }

        public static void main(String[] args) {
            ExecutorService executor1 = Executors.newCachedThreadPool();
            executor1.execute(new FirstRunable());
            executor1.execute(new SecondRunable());
            executor1.execute(new ThirdRunable());
            executor1.shutdown();
            ExecutorService executor2 = Executors.newFixedThreadPool(3);
            executor2.execute(new FirstRunable());
            executor2.execute(new SecondRunable());
            executor2.execute(new ThirdRunable());
            executor2.shutdown();
            ExecutorService executor3 = Executors.newSingleThreadExecutor();
            executor3.execute(new FirstRunable());
            executor3.execute(new SecondRunable());
            executor3.execute(new ThirdRunable());
            executor3.shutdown();
        }
}
