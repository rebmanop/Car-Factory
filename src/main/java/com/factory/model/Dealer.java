package com.factory.model;

import com.factory.CarFactory;
import com.factory.Controller;
import com.factory.model.entities.Car;

import java.util.concurrent.ThreadLocalRandom;


public class Dealer implements IPoolable, Runnable {

    private final Controller controller;
    private final Storage<Car> carStorage;

    public Dealer(Controller controller, Storage<Car> carStorage) {
        this.controller = controller;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        while (controller.isFactoryRunning()) {
            synchronized (controller.getPauseLock()) {
                if (!controller.isFactoryRunning()) break;
                if (controller.isFactoryPaused()) {
                    try {
                        controller.getPauseLock().wait();
                    } catch (InterruptedException ie) {
                        break;
                    }
                    if (!controller.isFactoryRunning()) {
                        break;
                    }
                }
            }
            try {
                int randomSaleTime = ThreadLocalRandom.current().nextInt(controller.getMinSaleTimeMs(), controller.getMaxSaleTimeMs() + 1);
                Thread.sleep(randomSaleTime);
            } catch(InterruptedException ie) {
                CarFactory.logger.fatal("Thread " + Thread.currentThread().getId() + " interrupted...");
            }

            Car car = carStorage.take();
            controller.incrementCarsSold();
            controller.decrementCarsStorageQuantity();
            CarFactory.logger.info("Dealer " + Thread.currentThread().getId() + ": " + car.toString() + " sold");
        }
    }
}
