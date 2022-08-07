public class EX15 {
    static class SyncTest1 {
        public void f1() {
            synchronized(this) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f1()");
                    Thread.yield();
                }
            }
        }
        public void g1() {
            synchronized(this) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g1()");
                    Thread.yield();
                }
            }
        }
        public void h1() {
            synchronized(this) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h1()");
                    Thread.yield();
                }
            }
        }
    }

    static class SyncTest2 { // methods synchronized on different objects
        private Object syncObject1 = new Object();
        private Object syncObject2 = new Object();
        private Object syncObject3 = new Object();
        public void f2() {
            synchronized(syncObject1) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f2()");
                    Thread.yield();
                }
            }
        }
        public void g2() {
            synchronized(syncObject2) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g2()");
                    Thread.yield();
                }
            }
        }
        public void h2() {
            synchronized(syncObject3) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h2()");
                    Thread.yield();
                }
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
