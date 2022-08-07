public class EX1 {
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
            return;
        }
    }

    static class SecondRunnable implements Runnable {
        public SecondRunnable() {
            System.out.println("Starting SecondRunable");
        }
        public void run() {
            for(int i = 0; i < 3; i++) {
                System.out.println("This is SecondRunable");
                Thread.yield();
            }
            System.out.println("SecondRunable task complete.");
            return;
        }
    }

    static class ThirdRunnable implements Runnable {
        public ThirdRunnable() {
            System.out.println("Starting ThirdRunable");
        }
        public void run() {
            for(int i = 0; i < 3; i++) {
                System.out.println("This is ThirdRunable");
                Thread.yield();
            }
            System.out.println("ThirdRunable task complete.");
            return;
        }
    }

        public static void main(String[] args) {
            Thread t1 = new Thread(new FirstRunable());
            Thread t2 = new Thread(new SecondRunnable());
            Thread t3 = new Thread(new ThirdRunnable());
            t1.start();
            t2.start();
            t3.start();
        }
}
