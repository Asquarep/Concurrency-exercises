import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EX16 {
    static class SyncTest1 { // all methods use same lock
        private Lock lock = new ReentrantLock();
        public void f1() {
            lock.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f1()");
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
        public void g1() {
            lock.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g1()");
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
        public void h1() {
            lock.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h1()");
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class SyncTest2 { // each method has a different lock
        private Lock lock1 = new ReentrantLock();
        private Lock lock2 = new ReentrantLock();
        private Lock lock3 = new ReentrantLock();

        public void f2() {
            lock1.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f2()");
                    Thread.yield();
                }
            } finally {
                lock1.unlock();
            }
        }
        public void g2() {
            lock2.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g2()");
                    Thread.yield();
                }
            } finally {
                lock2.unlock();
            }
        }
        public void h2() {
            lock3.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h2()");
                    Thread.yield();
                }
            } finally {
                lock3.unlock();
            }
        }
    }

        public static void main(String[] args) {
            final SyncTest1 st1 = new SyncTest1();
            final SyncTest2 st2 = new SyncTest2();
            new Thread() {
                public void run() {
                    st1.f1();
                }
            }.start();
            new Thread() {
                public void run() {
                    st1.g1();
                }
            }.start();
            new Thread() {
                public void run() {
                    st1.h1();
                }
            }.start();
            new Thread() {
                public void run() {
                    st2.f2();
                }
            }.start();
            new Thread() {
                public void run() {
                    st2.g2();
                }
            }.start();
            new Thread() {
                public void run() {
                    st2.h2();
                }
            }.start();
        }
}
