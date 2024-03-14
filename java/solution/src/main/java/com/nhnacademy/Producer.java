package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
    private Store store;
    String name;
    boolean isOut = false;

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public Producer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        if (isOut)
            return;
        isOut = true;
        logger.info("[생산자 입장] - {}",getName());
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10000));
            store.buy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info( "[생산자 퇴장] - {}", getName());
        isOut = false;
    }

}
