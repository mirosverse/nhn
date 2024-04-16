package com.nhnacademy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Store {
    private static final int MAX_WAIT_TIME = 3;
    private Item item; // 이 매장은 이 품목만을 취급한다.
    private ExecutorService producer;
    private ExecutorService consumer;
    private final Semaphore itemSemaphore;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    int itemCnt;
    int maxItemCnt;

    public Store(Item item, int maxItemCnt, int maxProducer, int maxConsumer) {
        this.item = item;
        this.maxItemCnt = maxItemCnt;
        this.itemCnt = 0;
        this.itemSemaphore = new Semaphore(maxItemCnt);
        this.producer = Executors.newFixedThreadPool(maxProducer);
        this.consumer = Executors.newFixedThreadPool(maxConsumer);
    }

    public void enter(Runnable person) {
        if (person instanceof Producer) {
            ((Producer) person).setStore(this);
            producer.submit(person);
        } else if (person instanceof Consumer) {
            consumer.submit(person);
            ((Consumer) person).setStore(this);
        }
    }

    public void shutdownNow() {
        producer.shutdownNow();
        consumer.shutdownNow();
    }

    public synchronized void buy() {
        try {
            if (!itemSemaphore.tryAcquire(MAX_WAIT_TIME, TimeUnit.SECONDS)) {
                logger.warn("[생산자 - {} 납품 포기] : 시간 초과", item.getName());
                return;
            }
            if (itemCnt >= maxItemCnt) {
                logger.warn("[생산자 - {} 납품 포기] : 수량 초과", item.getName());
                return;
            }
            itemCnt++;
            logger.info("[생산자 - {} 납품 완료]", item.getName());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[" + item.getName() + "] 물건 납품 대기중 .. ");

        } finally {
            itemSemaphore.release();
        }
    }

    public synchronized void sell() {
        try {
            if (!itemSemaphore.tryAcquire(MAX_WAIT_TIME, TimeUnit.SECONDS)) {
                logger.warn("[소비자 - {} 구매 포기] : 시간 초과", item.getName());
                return;
            }
            if (itemCnt == 0) {
                logger.warn("[소비자 - {} 구매 포기] : 수량 부족", item.getName());
                return;
            }
            itemCnt--;
            logger.info("[소비자 - {} 구매 완료]", item.getName());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[" + item.getName() + "] 물건 납품 대기중 .. ");

        } finally {
            itemSemaphore.release();
        }
    }

}
