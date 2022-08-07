import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EX13 {
    public static class SerialNumberGenerator13 {
	private static volatile int serialNumber = 0;
	public static synchronized int nextSerialNumber() {
		return serialNumber++;
	}
 }

  static class CircularSet {
        private int[] array;
        private int len;
        private int index = 0;
        public CircularSet(int size) {
            array = new int[size];
            len = size;
            for(int i = 0; i < size; i++)
                array[i] = -1;
        }
        public synchronized void add(int i) {
            array[index] = i;
            index = ++index % len;
        }
        public synchronized boolean contains(int val) {
            for(int i = 0; i < len; i++)
                if(array[i] == val) return true;
            return false;
        }
    }

        private static final int SIZE = 10;
        private static CircularSet serials = new CircularSet(1000);
        private static ExecutorService exec = Executors.newCachedThreadPool();
        static class SerialChecker13 implements Runnable {
            public void run() {
                while(true) {
                    int serial = SerialNumberGenerator13.nextSerialNumber();
                    if(serials.contains(serial)) {
                        System.out.println("Duplicate: " + serial);
                        System.exit(0);
                    }
                    serials.add(serial);
                }
            }
        }
        public static void main(String[] args) throws Exception {
            for(int i = 0; i < SIZE; i++)
                exec.execute(new SerialChecker13());
            Scanner scanner = new Scanner(System.in);
            int in = 0;
            System.out.println("Usage: enter a number");
            in= scanner.nextInt();
            if(in >= 1) {
                TimeUnit.SECONDS.sleep(in);
                System.out.println("No duplicates detected");
                System.exit(0);
            }
        }
}
