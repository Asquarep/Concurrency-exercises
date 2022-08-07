import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EX22 {
    static class A implements Runnable {
        boolean flag = false;
        public synchronized void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
                System.out.println("sleep interrupted in A");
            }
            System.out.println("A setting flag = true");
            flag = true;
        }
    }

    static class BusyWait implements Runnable {
        A a = new A();
        long start, end;
        public synchronized A getA() { return a; }
        private BusyWait(A a) {
            this.a = a;
        }
        public static BusyWait buildBusyWait(A a) {
            return new BusyWait(a);
        }
        public synchronized void run() {
            System.out.println("Busy a.flag = " + a.flag);
            while(!Thread.interrupted()) {
                start = System.nanoTime();
                if(a.flag) {
                    a.flag = false;
                    System.out.println("BusyWait reset a.flag = false");
                    end = System.nanoTime();
                    System.out.println("Busy waiting " + (end - start) + " nanoseconds");
                }
            }
        }
    }

    static class BetterWait implements Runnable {
        private A a = new A();
        public synchronized A getA() { return a; }
        private BetterWait(A a) {
            this.a = a;
        }
        public static BetterWait buildBetterWait(A a) {
            return new BetterWait(a);
        }
        public synchronized void run() {
            System.out.println("Better a.flag = " + a.flag);
            try {
                while(!a.flag) {
                    wait();
                    a.flag = false;
                    System.out.println("BetterWait reset a.flag = false");
                }
            } catch(InterruptedException e) {
                System.out.println("BetterWait.run() interrupted");
            }
        }
    }

        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            BusyWait busy = BusyWait.buildBusyWait(new A());
            exec.execute(busy.a);
            exec.execute(busy);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(InterruptedException e) {
                System.out.println("sleep interrupted in main()");
            }
            System.out.println();
            BetterWait better = BetterWait.buildBetterWait(new A());
            exec.execute(better.getA());
            exec.execute(better);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(InterruptedException e) {
                System.out.println("sleep interrupted in main()");
            }
            synchronized(better) {
                System.out.println("Sending better.notifyAll()");
                better.notifyAll();
            }
            exec.shutdownNow();
        }
}
