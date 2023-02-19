package com.factory.model;

import java.util.*;

import com.factory.CarFactory;


public class Storage<T extends IStorable> {
    private final int capacity;
    private final Stack<T> items = new Stack<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) {
        synchronized (items) {
            while (items.size() >= capacity) {
                try {          items.wait();
                } catch (InterruptedException e) {
                    CarFactory.logger.error("Thread " + Thread.currentThread().getName() + " closed.");
                }
            }

            items.push(item);
            items.notify();
        }
    }

    public T take() {
        synchronized (items) {
            while (items.size() == 0) {
                try {
                    items.wait();
                } catch (InterruptedException e) {
                    CarFactory.logger.error("Thread " + Thread.currentThread().getName() + " closed.");
                }
            }

            T item = items.pop();
            items.notify();
            return item;
        }
    }
}