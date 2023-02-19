package com.factory.model.suppliers;

import com.factory.CarFactory;
import com.factory.Controller;
import com.factory.model.IPoolable;
import com.factory.model.Storage;
import com.factory.model.entities.Accessories;

public class AccessoriesSupplier implements IPoolable, Runnable {
    private final Controller controller;
    private final Storage<Accessories> storage;

    public AccessoriesSupplier(Controller controller, Storage<Accessories> storage) {
        this.controller = controller;
        this.storage = storage;
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
                Thread.sleep(controller.getAccessoriesDeliveryTimeMs());

            } catch (InterruptedException e) {
                CarFactory.logger.fatal("Accessories supplier thread" + Thread.currentThread().getId() + "died...");
            }

            Accessories accessories = new Accessories();

            storage.put(accessories);
            controller.incrementAccessoriesStorageQuantity();

            CarFactory.logger.info("Accessories supplier " + Thread.currentThread().getId() + ": accessories " + accessories.getID() + " put in storage");
        }
    }
}