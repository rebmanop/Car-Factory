package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Storage;
import com.factory.model.entities.Body;
import com.factory.model.suppliers.BodySupplier;
import java.util.concurrent.Executors;

public class BodySupplierPool extends FactoryPool {
    public BodySupplierPool(Controller controller, Storage<Body> storage, int numberOfThreads)  {
        super(storage);
        this.controller = controller;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new BodySupplier(this.controller, storage));
        }
    }

}
