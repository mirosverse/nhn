public class SelfRunnableCounter implements Runnable {
    int count;
    int maxCount;
    Thread thread;

    public SelfRunnableCounter(String name, int maxCount) {
        this.maxCount = maxCount;
        count = 0;
        thread = new Thread(this, name);
    }

    public void start() {
        thread.start();
    }

    public void stop(){
        thread.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && count < maxCount) {
            try {
                Thread.sleep(1000);
                count++;
                System.out.println(thread.getName() + " : " + count);
            } catch (InterruptedException e) {
                // System.out.println(name+"interrupted, "+ Thread.currentThread().getName());
                thread.interrupt();

            }

        }
    }

    public int getCount() {
        return count;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean isAlive(){
        return thread.isAlive();
    }

    
}

 class Exam {
    public static void main(String[] args) {
        SelfRunnableCounter counter1 = new SelfRunnableCounter("counter1", 10);
        SelfRunnableCounter counter2 = new SelfRunnableCounter("counter2", 10);

        System.out.println(Thread.currentThread().toString());
        counter1.start();
        counter2.start();
        
        while (counter1.isAlive() || counter2.isAlive()) {
            if ((counter1.getCount() > 5) && (counter2.getCount() > 5)) {
                counter1.stop();
                counter2.stop();
            }
        }
    }
}