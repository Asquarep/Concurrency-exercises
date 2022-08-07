import Extras.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EX20 {
        public static void main(String[] args) throws Exception {
            System.out.println("Using LiftOff:");
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < 5; i++) {
                Future<?> f = exec.submit(new LiftOff());
                f.cancel(true);
            }
            exec.shutdownNow();
            if(exec.awaitTermination(250, TimeUnit.MILLISECONDS))
                System.out.println("Some tasks were not terminated");

            System.out.println("\nUsing LiftOff20:");
            ExecutorService exec20 = Executors.newCachedThreadPool();
            for(int i = 0; i < 5; i++) {
                Future<?> f = exec20.submit(new LiftOff());
                f.cancel(true);
            }
            exec20.shutdownNow();
            if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
                System.out.println("Some tasks were not terminated");
        }
}
