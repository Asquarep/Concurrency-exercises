import Extras.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EX27WaitPersons {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public EX27WaitPersons() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    static class WaitPerson implements Runnable {
        private EX27WaitPersons restaurant;
        protected Lock lock = new ReentrantLock();
        protected Condition condition = lock.newCondition();
        public WaitPerson(EX27WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    lock.lock();
                    try {
                        while(restaurant.meal == null)
                            condition.await();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println("Person got " + restaurant.meal);
                    restaurant.chef.lock.lock();
                    try {
                        restaurant.meal = null;
                        restaurant.chef.condition.signalAll();
                    } finally {
                        restaurant.chef.lock.unlock();
                    }
                }
            } catch(InterruptedException e) {
                System.out.println("Person interrupted");
            }
        }
    }

    static class Chef implements Runnable {
        private EX27WaitPersons restaurant;
        private int count = 0;
        protected Lock lock = new ReentrantLock();
        protected Condition condition = lock.newCondition();
        public Chef(EX27WaitPersons r) { restaurant = r; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    lock.lock();
                    try {
                        while(restaurant.meal != null)
                            condition.await();
                    } finally {
                        lock.unlock();
                    }
                    if(++count == 10) {
                        System.out.println("Out of food, closing");
                        restaurant.exec.shutdownNow();
                        return;
                    }
                    System.out.println("Order up! ");
                    restaurant.waitPerson.lock.lock();
                    try {
                        restaurant.meal = new Meal(count);
                        restaurant.waitPerson.condition.signalAll();
                    } finally {
                        restaurant.waitPerson.lock.unlock();
                    }
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch(InterruptedException e) {
                System.out.println("chef interrupted");
            }
        }
    }

        public static void main(String[] args) {
            new EX27WaitPersons();
        }
}
