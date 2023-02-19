package com.factory.model.entities;

import com.factory.model.IStorable;

public class Car extends Entity implements IStorable {

    private Engine engine;
    private Body body;
    private Accessories accessories;

    public Car(Engine engine, Body body, Accessories accessories) {
        this.engine = engine;
        this.body = body;
        this.accessories = accessories;
    }

    @Override
    public String toString() {
        return "car " + getID() + " (engine: " + engine.getID() + ", " +
                "body: " + body.getID() + ", accessories: " + accessories.getID() + ")";
    }


}
