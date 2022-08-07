import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EX4 {
    static class Fib1 implements Runnable {
        private int n = 0;
        public Fib1(int n) {
            this.n = n;
        }
        private int fib(int x) {
            if(x < 2) return 1;
            return fib(x - 2) + fib(x - 1);
        }
        public void run() {
            for(int i = 0; i < n; i++)
                System.out.println(fib(i) + " ");
        }
    }

    static class Fib2 implements Runnable {
        private int n = 0;
        public Fib2(int n) {
            this.n = n;
        }
        private int fib(int x) {
            if(x < 2) return 1;
            return fib(x - 2) + fib(x - 1);
        }
        public void run() {
            for(int i = 0; i < n; i++)
                System.out.println(fib(i) + " ");
        }
    }

    static class Fib3 implements Runnable {
        private int n = 0;
        public Fib3(int n) {
            this.n = n;
        }
        private int fib(int x) {
            if(x < 2) return 1;
            return fib(x - 2) + fib(x - 1);
        }
        public void run() {
            for(int i = 0; i < n; i++)
                System.out.println(fib(i) + " ");
        }
    }

    static class Fib4 implements Runnable {
        private int n = 0;
        public Fib4(int n) {
            this.n = n;
        }
        private int fib(int x) {
            if(x < 2) return 1;
            return fib(x - 2) + fib(x - 1);
        }
        public void run() {
            for(int i = 0; i < n; i++)
                System.out.println(fib(i) + " ");
        }
    }

        public static void main(String[] args) {
            ExecutorService executor1 = Executors.newCachedThreadPool();
            executor1.execute(new Fib1(15));
            executor1.execute(new Fib2(15));
            executor1.execute(new Fib3(15));
            executor1.execute(new Fib4(15));
            executor1.shutdown();
            ExecutorService executor2 = Executors.newFixedThreadPool(4);
            executor2.execute(new Fib1(15));
            executor2.execute(new Fib2(15));
            executor2.execute(new Fib3(15));
            executor2.execute(new Fib4(15));
            executor2.shutdown();
            ExecutorService executor3 = Executors.newSingleThreadExecutor();
            executor3.execute(new Fib1(15));
            executor3.execute(new Fib2(15));
            executor3.execute(new Fib3(15));
            executor3.execute(new Fib4(15));
            executor3.shutdown();
        }
}
