package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Assembler;
import com.factory.model.Storage;
import com.factory.model.entities.Accessories;
import com.factory.model.entities.Body;
import com.factory.model.entities.Car;
import com.factory.model.entities.Engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssemblersPool extends FactoryPool {

    private Storage<Car> carStorage;
    private Storage<Engine> engineStorage;
    private Storage<Body> bodyStorage;
    private Storage<Accessories> accessoriesStorage;


    public AssemblersPool(Controller controller, Storage<Car> carStorage, Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                          Storage<Accessories> accessoriesStorage, int numberOfThreads)  {
        this.controller = controller;
        this.carStorage = carStorage;
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoriesStorage = accessoriesStorage;
        this.numberOfThreads = numberOfThreads;
    }


    @Override
    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new Assembler(this.controller, engineStorage, bodyStorage, accessoriesStorage, carStorage));
        }
    }

}
