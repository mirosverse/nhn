import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker implements Runnable {
    String name;

    public Worker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println(getName() + " started ");
        try {
            Thread.sleep(1999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " finished");
    }
}

class Exam01 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executor.submit(new Worker("Worker" + i));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        
        System.out.println("모든 작업이 완료되었습니다.");
    }

}