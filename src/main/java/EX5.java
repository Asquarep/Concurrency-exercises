import java.util.*;
import java.util.concurrent.*;

public class EX5 {
    static class Fib5 implements Callable<Integer> {
        private int n = 0;
        public Fib5(int n) {
            this.n = n;
        }
        private int fib(int x) {
            if(x < 2) return 1;
            return fib(x - 2) + fib(x - 1);
        }
        public Integer call() {
            int result = 0;
            for(int i = 0; i < n; i++)
                result += fib(i);
            return result;
        }
    }

        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            ArrayList<Future<Integer>> results = new ArrayList<>();
            for(int i = 0; i < 20; i++)
                results.add(exec.submit(new Fib5(i)));
            for(Future<Integer> future : results)
                try {
                    System.out.println(future.get());
                } catch(InterruptedException e) {
                    System.out.println(e);
                    return;
                } catch(ExecutionException e) {
                    System.out.println(e);
                } finally {
                    exec.shutdown();
                }
        }
}
