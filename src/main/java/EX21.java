import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EX21 {
    static class A implements Runnable {
        private volatile boolean signal = false;
        public synchronized void run() {
            try {
                while(!signal) {
                    System.out.println("A.run() wait()");

                    wait();
                    signal = true;
                    System.out.println("A is done waiting");
                }
            } catch(InterruptedException e) {
                System.out.println("A run() interrupted");
            } finally {
                System.out.println("Leaving A.run()");
            }
        }
        public synchronized void message() {
            System.out.println("Hi from A");
        }
    }

    static class B implements Runnable {
        private A a;
        public A getA() { return a; }
        B(A a) { this.a = a; }
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                synchronized(a) { // method calling notifyAll() must own monitor
                    System.out.println("B.run() a.notifyAll()");
                    a.notifyAll();
                }
            } catch(InterruptedException e) {
                System.out.println("B sleep interrupted");
            }
        }
    }

        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            B b = new B(new A());
            exec.execute(b.getA());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch(InterruptedException e) {
                System.out.println("main() sleep interrupted");
            }
            b.getA().message();
            exec.execute(b);
            try {
                TimeUnit.MILLISECONDS.sleep(2500);
            } catch(InterruptedException e) {
                System.out.println("main() sleep interrupted");
            }
            exec.shutdownNow();
        }
}
