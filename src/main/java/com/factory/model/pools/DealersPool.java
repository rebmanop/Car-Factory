package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Dealer;
import com.factory.model.Storage;
import com.factory.model.entities.Car;
import java.util.concurrent.Executors;

public class DealersPool extends FactoryPool {

    public DealersPool(Controller controller, Storage<Car> storage, int numberOfThreads) {
        super(storage);
        this.controller = controller;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new Dealer(this.controller, storage));
        }
    }
}
