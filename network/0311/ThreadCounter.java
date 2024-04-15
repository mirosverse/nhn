import java.time.LocalTime;

public class ThreadCounter extends Thread {
    private String name;
    private int maxCount;

    public ThreadCounter(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < maxCount) {
            try {
                Thread.sleep(1000);
                count++;
                System.out.println(name + " : " + count);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}

class Test {
    public static void main(String[] args) {
        ThreadCounter counter1 = new ThreadCounter("counter1", 10);
        ThreadCounter counter2 = new ThreadCounter("counter2", 10);
        System.out.println("start : " + LocalTime.now());
        counter1.start();
        counter2.start();

        while (counter1.isAlive() || counter2.isAlive() ) {
            ;
        }
        System.out.println("end : " + LocalTime.now());

    }
}
