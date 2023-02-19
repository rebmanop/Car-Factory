package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Storage;
import com.factory.model.entities.Accessories;
import com.factory.model.suppliers.AccessoriesSupplier;
import java.util.concurrent.Executors;

public class AccessoriesSupplierPool extends FactoryPool {


    public AccessoriesSupplierPool(Controller controller, Storage<Accessories> storage, int numberOfThreads)  {
        super(storage);
        this.controller = controller;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new AccessoriesSupplier(this.controller, storage));
        }
    }
}
