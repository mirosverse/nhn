public class Counter {
    private String name;
    private int maxCount;

    public Counter(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    public void run() throws InterruptedException {
        int count = 0;
        while (count < maxCount) {
            Thread.sleep(1000);
            count++;
            System.out.println(name + " : " + count);
        }
    }
}

/**
 * Test
 * 
 */

class Test {
    public static void main(String[] args) throws InterruptedException {
        Counter counter1 = new Counter("counter1", 10);
        Counter counter2 = new Counter("counter2", 10);
        counter1.run();
        counter2.run();
    
    }
}
