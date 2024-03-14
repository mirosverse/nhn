import java.util.concurrent.ThreadLocalRandom;

public class Data {
    private String packet;

    // true이면 sender 작업중 -> receive wait
    // false이면 receive 작업중 -> sender wait
    private boolean transfer = true;

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();     // sender가 데이터를 보낼때까지 기다림
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        transfer = true;

        String returnPacket = packet;
        notifyAll();
        return returnPacket;
    }

    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.err.println("ThreadInterrupted");
            }
        }
        transfer = false;       // true면 바로 실행, 그 후 false로 바꿈

        this.packet = packet;
        notifyAll();
    }

}

class Sender implements Runnable {
    private Data data;

    // standard constructors

    public void run() {
        String packets[] = {
                "First packet",
                "Second packet",
                "Third packet",
                "Fourth packet",
                "End"
        };

        for (String packet : packets) {
            data.send(packet);

            // Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
    }

    public Sender(Data data) {
        this.data = data;
    }
}

class Receiver implements Runnable {
    private Data load;

    // standard constructors

    public void run() {
        for (String receivedMessage = load.receive(); 
        !"End".equals(receivedMessage); 
        receivedMessage = load.receive()) {

            System.out.println(receivedMessage);

            // Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
    }

    public Receiver(Data load) {
        this.load = load;
    }
}

class Exam03 {
    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data), "sender");
        Thread receiver = new Thread(new Receiver(data), "receiver");
        sender.start();
        receiver.start();
    }

}