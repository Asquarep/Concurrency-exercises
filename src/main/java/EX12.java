import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EX12 implements Runnable {
        private int i = 0;
        public synchronized int getValue() { return i; }
        private synchronized void evenIncrement() { i++; i++; }
        public void run() {
            while(true) {
                evenIncrement();
            }
        }
        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            EX12 at = new EX12();
            exec.execute(at);
            while(true) {
                int val = at.getValue();
                if(val % 2 != 0) {
                    System.out.println(val);
                    System.exit(0);
                }
            }
        }
}
