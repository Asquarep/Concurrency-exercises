import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EX11 {
    abstract static class RangeGenerator {
        private volatile boolean canceled = false;
        public abstract int[] next();
        public void cancel() { canceled = true; }
        public boolean isCanceled() { return canceled; }
    }

    static class NumRangeCheck implements Runnable {
        private RangeGenerator generator;
        private final int id;
        public NumRangeCheck(RangeGenerator g, int ident) {
            generator = g;
            id = ident;
        }
        public void run() {
            System.out.println("Testing..");
            while(!generator.isCanceled()) {
                int[] range = generator.next();
                if( range[0] > range[1]) {
                    System.out.println("Error in test #" + id + ": min " + range[0]
                            + " > " + "max " + range[1] );
                    generator.cancel();
                }
            }
        }
        public static void test(RangeGenerator gen, int count) {
            System.out.println("Press Ctrl-C to exit");
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < count; i++)
                exec.execute(new NumRangeCheck(gen, i));
            exec.shutdown();
        }
        public static void test(RangeGenerator gen) {
            test(gen, 10);
        }
    }

    public static class NumRangeGenerator11 extends RangeGenerator {
        private int min = 0;
        private int max = 0;
        private int[] range = { min, max };
        private Random rand = new Random();
        public int[] next() { // oops, method should be synchronized
            min = rand.nextInt(100);
            max = rand.nextInt(100);
            Thread.yield();
            if(min > max) max = min;
            int[] ia = { min, max };
            return ia;
        }
        public static void main(String[] args) {
            NumRangeCheck.test(new NumRangeGenerator11());
        }
    }

//* Synchronized version that runs without error:


  public static class SynchronizedNumRangeGenerator11 extends RangeGenerator {
 	private int min = 0;
 	private int max = 0;
 	private int[] range = { min, max };
 	private Random rand = new Random();
 	public synchronized int[] next() { // synchronized!
 		min = rand.nextInt(100);
 		max = rand.nextInt(100);
 		Thread.yield();
 		if(min > max) max = min;
 		int[] ia = { min, max };
 		return ia;
 	}
 	public static void main(String[] args) {
 		NumRangeCheck.test(new SynchronizedNumRangeGenerator11());
 	}
  }

}
