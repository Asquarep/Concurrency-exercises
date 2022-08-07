import Extras.LiftOff;

public class EX8 {
        public static void main(String[] args) {
            try {
                for(int i = 0; i < 25; i++) {
                    Thread t = new Thread(new LiftOff());
                    t.setDaemon(true);
                    t.start();
                }
                System.out.println("Waiting for LiftOff");
            } finally {
                System.out.println("Finally out of main");
            }
        }
}
