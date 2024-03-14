package com.nhnacademy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Market {
    private Random random = new Random();

    List<Store> stores;
    List<Consumer> consumers;
    List<Producer> producers;

    public Market() {
        this.stores = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.producers = new ArrayList<>();
        createStores();
        createConsumers(10);
        createProducers(10);
    }

    // public void run() {
    //     for (Consumer consumer : consumers) {
    //         visitRandomStore(consumer);
    //     }
    //     for (Producer producer : producers) {
    //         visitRandomStore(producer);
    //     }
    // }


    public void run() {
        for (int i = 0; i < 100; i++) {
            visitRandomStore(consumers.get(random.nextInt(consumers.size())));
            visitRandomStore(producers.get(random.nextInt(producers.size())));
        }

        try{
            Thread.sleep(300000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted");
        }
    }

    public void visitRandomStore(Runnable person) {
        Store store = stores.get(random.nextInt(stores.size()));
        store.enter(person);
    }

    public void createStores() {
        stores.addAll(Arrays.asList(
                new Store(new Item("사과"), 5, 3, 3),
                new Store(new Item("복숭아"), 5, 2, 3),
                new Store(new Item("리치"), 5, 2, 2)));
    }

    public void createConsumers(int n) {
        for (int i = 0; i < n; i++) {
            consumers.add(new Consumer("consumer" + i));
        }
    }

    public void createProducers(int n) {
        for (int i = 0; i < n; i++) {
            producers.add(new Producer("producer" + i));
        }
    }

    public List<Store> getStores() {
        return stores;
    }

    public Store getStore(int index) {
        return stores.get(index);
    }

    public void close() {
        for (Store store : stores) {
            store.shutdownNow();
        }
    }

}
