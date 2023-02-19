package com.factory.model.suppliers;

import com.factory.CarFactory;
import com.factory.Controller;
import com.factory.model.IPoolable;
import com.factory.model.Storage;
import com.factory.model.entities.Engine;

public class EngineSupplier implements IPoolable, Runnable {
    private final Controller controller;
    private final Storage<Engine> storage;

    public EngineSupplier(Controller controller, Storage<Engine> storage) {
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
                Thread.sleep(controller.getEngineDeliveryTimeMs());
            } catch (InterruptedException e) {
                CarFactory.logger.fatal("Engine supplier thread" + Thread.currentThread().getId() + "died...");
            }
            Engine engine = new Engine();
            storage.put(engine);
            controller.incrementEngineStorageQuantity();
            CarFactory.logger.info("Engine supplier " + Thread.currentThread().getId() + ": engine " + engine.getID() + " put in storage");
        }
    }
}
