public class Exam03 {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("group");
        Worker thread1 = new Worker(group, "worker1");
        Worker thread2 = new Worker("worker2");

        thread1.start();
        thread2.start();

        Thread.sleep(5000);
        group.interrupt();

    }
}
