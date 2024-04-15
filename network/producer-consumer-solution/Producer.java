import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private Store store;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,10000));
            store.buy();
            System.out.println(" 생상자 : 물건 판매 완료!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
