import Extras.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EX26WaitPersons {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    BusBoy busBoy = new BusBoy(this);
    Chef chef = new Chef(this);

    public EX26WaitPersons() {
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busBoy);
    }

    static class WaitPerson implements Runnable {
        private EX26WaitPersons restaurant;
        protected boolean clean = true;
        protected Meal m; //
        public WaitPerson(EX26WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    synchronized(this) {
                        while(restaurant.meal == null)
                            wait();
                    }
                    m = restaurant.meal;
                    System.out.println("Person got " + m);
                    synchronized(restaurant.chef) {
                        restaurant.meal = null;
                        restaurant.chef.notifyAll();
                    }
                    System.out.println("Person delivered " + m);
                    synchronized(restaurant.busBoy) {
                        clean = false;
                        restaurant.busBoy.notifyAll(); // for cleanup
                    }
                }
            } catch(InterruptedException e) {
                System.out.println("Person interrupted");
            }
        }
    }

    static class Chef implements Runnable {
        private EX26WaitPersons restaurant;
        private int count = 0;
        public Chef(EX26WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    synchronized(this) {
                        while(restaurant.meal != null)
                            wait(); // ... for the meal to be taken
                    }
                    if(++count == 10) {
                        System.out.println("Out of food, closing");
                        restaurant.exec.shutdownNow();
                        return;
                    }
                    System.out.println("Order up! ");
                    synchronized(restaurant.waitPerson) {
                        restaurant.meal = new Meal(count);
                        restaurant.waitPerson.notifyAll();
                    }
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch(InterruptedException e) {
                System.out.println("Chef interrupted");
            }
        }
    }

    static class BusBoy implements Runnable {
        private EX26WaitPersons restaurant;
        public BusBoy(EX26WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    synchronized(this) {
                        while(restaurant.waitPerson.clean)
                            wait();
                    }
                    System.out.println("BusBoy cleaning up " + restaurant.waitPerson.m);
                    restaurant.waitPerson.clean = true;
                }
            } catch(InterruptedException e) {
                System.out.println("BusBoy interrupted");
            }
        }
    }

        public static void main(String[] args) {
            new EX26WaitPersons();
        }
}
