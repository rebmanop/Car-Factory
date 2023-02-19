package com.factory.model.pools;

import com.factory.Controller;
import com.factory.model.Storage;

import java.util.concurrent.ExecutorService;

abstract public class FactoryPool {
    protected ExecutorService threadPool;
    protected Controller controller;
    protected Storage storage;
    protected int numberOfThreads;

    public FactoryPool(Storage storage) {
        this.storage = storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public FactoryPool() {}

    public abstract void startThreads();

    public void shutdownThreads() {
        threadPool.shutdownNow();
    }
}
