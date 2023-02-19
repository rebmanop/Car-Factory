package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Storage;
import com.factory.model.entities.Engine;
import com.factory.model.suppliers.EngineSupplier;
import java.util.concurrent.Executors;

public class EngineSupplierPool extends FactoryPool {


    public EngineSupplierPool(Controller controller, Storage<Engine> storage, int numberOfThreads)  {
        super(storage);
        this.controller = controller;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new EngineSupplier(this.controller, storage));
        }
    }
}
