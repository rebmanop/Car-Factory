package com.factory.model.suppliers;

import com.factory.CarFactory;
import com.factory.Controller;
import com.factory.model.IPoolable;
import com.factory.model.Storage;
import com.factory.model.entities.Body;

public class BodySupplier implements IPoolable, Runnable {
    private final Controller controller;
    private final Storage<Body> storage;

    public BodySupplier(Controller controller, Storage<Body> storage) {
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
                Thread.sleep(controller.getBodyDeliveryTimeMs());
            } catch (InterruptedException e) {
                CarFactory.logger.fatal("Body supplier thread" + Thread.currentThread().getId() + "died...");
            }

            Body body = new Body();
            storage.put(body);
            controller.incrementBodyStorageQuantity();
            CarFactory.logger.info("Body supplier " + Thread.currentThread().getId() + ": body " + body.getID() + " put in storage");
        }
    }
}
