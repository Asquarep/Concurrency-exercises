import java.util.concurrent.*;


public class EX10 {
    static class Fib10 implements Callable<Integer> {
        private Integer n = 0;
        ExecutorService exec = Executors.newSingleThreadExecutor();
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
        public Future<Integer> runTask(Integer n) {
            this.n = n;
            return exec.submit(this);
        }
    }

    public static void main(String[] args) {
        Fib10 fib = new Fib10();
        try {
            for(int i = 0; i < 10; i++) {
                System.out.println("The sum of the first " + i + " fibonacci numbers is " + fib.runTask(i).get());
            }
        } catch(InterruptedException | ExecutionException e) {
            System.out.println(e);
        } finally {
            fib.exec.shutdown();
        }
    }
}
