import Extras.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class EX25WaitPersons {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public EX25WaitPersons() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }


    static class WaitPerson implements Runnable {
        private EX25WaitPersons restaurant;
        public WaitPerson(EX25WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    synchronized(this) {
                        while(restaurant.meal == null)
                            wait(); // ... for the chef to produce a meal
                    }
                    System.out.println("Person got " + restaurant.meal);
                    synchronized(restaurant.chef) {
                        restaurant.meal = null;
                        restaurant.chef.notifyAll(); // ready for another
                    }
                }
            } catch(InterruptedException e) {
                System.out.println("Person interrupted");
            }
        }
    }

    static class Chef implements Runnable {
        private EX25WaitPersons restaurant;
        private int count = 0;
        public Chef(EX25WaitPersons r) { restaurant = r; }
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
                        return; // now Chef won't make another meal
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


        public static void main(String[] args) {
            new EX25WaitPersons();
        }

}
