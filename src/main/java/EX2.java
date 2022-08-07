public class EX2 {
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
            Thread f1 = new Thread(new Fib1(15));
            Thread f2 = new Thread(new Fib2(15));
            Thread f3 = new Thread(new Fib3(15));
            Thread f4 = new Thread(new Fib4(15));
            f1.start();
            f2.start();
            f3.start();
            f4.start();
        }
}
