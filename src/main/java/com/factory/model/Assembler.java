package com.factory.model;

import com.factory.CarFactory;
import com.factory.Controller;
import com.factory.model.entities.Accessories;
import com.factory.model.entities.Body;
import com.factory.model.entities.Car;
import com.factory.model.entities.Engine;

public class Assembler implements IPoolable, Runnable {

    private final Controller controller;
    private final Storage<Car> carStorage;

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessories> accessoriesStorage;


    public Assembler(Controller controller, Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                     Storage<Accessories> accessoriesStorage, Storage<Car> carStorage) {
        this.controller = controller;
        this.carStorage = carStorage;
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoriesStorage = accessoriesStorage;
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
                Thread.sleep(controller.getCarAssemblyTimeMs());
            } catch (InterruptedException ie) {
                CarFactory.logger.fatal("Thread " + Thread.currentThread().getName() + " interrupted...");
            }

            Car car = new Car(engineStorage.take(), bodyStorage.take(), accessoriesStorage.take());
            controller.decrementBodyStorageQuantity();
            controller.decrementEngineStorageQuantity();
            controller.decrementAccessoriesStorageQuantity();
            controller.incrementCarsManufactured();

            carStorage.put(car);
            controller.incrementCarsStorageQuantity();

            CarFactory.logger.info("Assembler " + Thread.currentThread().getId() + ": " + car.toString() + " assembled and put in storage");
        }
    }
}
