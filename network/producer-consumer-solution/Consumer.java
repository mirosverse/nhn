import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    String name;
    Store store;

    public Consumer(String name, Store store) {
        this.name = name;
        this.store = store;
    }

    @Override
    public void run() {

        try {
            store.enter();
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,10000));
            store.sell();
            System.out.println( name+" : 물건 구매 완료 'ㅅ'");
            store.exit();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
