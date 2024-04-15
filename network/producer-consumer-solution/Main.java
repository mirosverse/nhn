//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Store store = new Store();


        for (int i = 0; i < 3; i++) {
            Thread producer = new Thread(new Producer(store), "producer");
            Thread consumer = new Thread(new Consumer("consumer" + i, store), "consumer");
            producer.start();
            consumer.start();
        }
    }
}