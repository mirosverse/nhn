import java.time.LocalTime;

public class RunnableCounter implements Runnable {

    private String name;
    private int count;
    private int maxCount;

    public RunnableCounter(String name, int maxCount) {
        this.name = name;
        this.count = 0;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        while (count < maxCount) {
            try {
                Thread.sleep(1000);
                this.count++;
                System.out.println(name + " : " + count);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public String getName() {
        return name;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getCount() {
        return count;
    }

}

class Test3 {
    public static void main(String[] args) {
        RunnableCounter[] counters = new RunnableCounter[10];
        Thread[] threads = new Thread[10];

        System.out.println("start: " + LocalTime.now());

        for (int i = 0; i < counters.length; i++) {
            counters[i] = new RunnableCounter("counter" + (i + 1), 10);
            threads[i] = new Thread(counters[i]);
            threads[i].start();
        }

        // while (true) {
        //     boolean allStopped = true;
        //     for (int i = 0; i < threads.length; i++) {
        //         if (threads[i].isAlive()) {
        //             allStopped = false;
        //         }
        //     }
        // }

        RunnableCounter counter1 = new RunnableCounter("counter1", 10);
        RunnableCounter counter2 = new RunnableCounter("counter2", 10);
        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
            if ((counter1.getCount() > 5) && (counter2.getCount() > 5)) {
                thread1.interrupt();
                thread2.interrupt();
            }
        }

    }
}