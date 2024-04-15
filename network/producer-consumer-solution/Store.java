public class Store {
    int customerCnt;
    int itemCnt;
    int maxCustomerCnt = 5;
    int maxItemCnt = 10;

    private boolean isBuying = false;
    private boolean isSelling = false;

    public Store() {
        customerCnt = 0;
        itemCnt = 0;
    }

    public void enter(){
        while (customerCnt>maxCustomerCnt){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        customerCnt++;
        System.out.println("손님 입장~ (현 인원: "+customerCnt +")");
    }

    public void exit(){
        while (customerCnt == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        customerCnt--;
        System.out.println("손님 퇴장~ (현 인원: "+customerCnt +")");
    }

    public synchronized void buy() {
        while (itemCnt > maxItemCnt || isBuying){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("물건 납품 대기중 .. ");
            }
        }
        isBuying = true;
        itemCnt++;
        isBuying = false;
        notifyAll();
    }

    public  synchronized void sell(){
        while (itemCnt == 0 || isSelling){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("물건 구매 대기중 .. ");
            }
        }
        isSelling = true;
        itemCnt--;
        isSelling = false;
        notifyAll();
    }

}
