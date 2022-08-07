import Extras.LiftOff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class EX28 {
    static class LiftOffRunner implements Runnable {
        private BlockingQueue<LiftOff> rockets;
        public LiftOffRunner(BlockingQueue<LiftOff> queue) {
            rockets = queue;
        }
        public void add(LiftOff lo) {
            try {
                rockets.put(lo);
            } catch(InterruptedException e) {
                System.out.println("Interrupted during put()");
            }
        }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    LiftOff rocket = rockets.take();
                    rocket.run(); // Use this thread
                }
            } catch(InterruptedException e) {
                System.out.println("Waking from take()");
            }
            System.out.println("Exiting LiftOffRunner");
        }
    }

    static class LiftOffAdder implements Runnable {
        private LiftOffRunner runner;
        public LiftOffAdder(LiftOffRunner runner) {
            this.runner = runner;
        }
        public void run() {
            for(int i = 0; i < 5; i++)
                runner.add(new LiftOff(5));
        }
    }

    public static class TestBlockingQueues28 {
        static void getKey() {
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch(java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
        static void getKey(String message) {
            System.out.println(message);
            getKey();
        }
        static void test(String msg, BlockingQueue<LiftOff> queue) {
            System.out.println(msg);
            LiftOffRunner runner = new LiftOffRunner(queue);
            LiftOffAdder adder = new LiftOffAdder(runner);
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(runner);
            exec.execute(adder);
            getKey("Press 'Enter' (" + msg + ")");
            System.out.println("Finished " + msg + " test");
            exec.shutdownNow();
        }
        public static void main(String[] args) {
            test("LinkedBlockingQueue",
                    new LinkedBlockingQueue<>());
            test("ArrayBlockingQueue",
                    new ArrayBlockingQueue<>(3));
            test("SynchronousQueue",
                    new SynchronousQueue<>());
        }
    }
}
