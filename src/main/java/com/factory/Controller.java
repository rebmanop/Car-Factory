package com.factory;

import com.factory.model.pools.FactoryPool;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Controller {

    private volatile boolean factoryIsRunning = false;
    private volatile boolean factoryIsPaused = false;
    private final Object pauseLock = new Object();

    private FactoryPool[] threadPools;

    private int engineDeliveryTimeMs = 2500;
    private int accessoriesDeliveryTimeMs = 2500;
    private int bodyDeliveryTimeMs = 2500;
    private int carAssemblyTimeMs = 2500;

    private final int minSaleTimeMs = 1000;
    private final int maxSaleTimeMs = 5000;

    private int bodyStorageQuantity =  0;
    private int engineStorageQuantity = 0;
    private int accessoriesStorageQuantity = 0;
    private int carsStorageQuantity = 0;
    private int carsManufactured = 0;
    private int carsSold = 0;

    public Button startFactoryButton;
    public Button resetButton;

    public Label bodyDeliverySpeedLabel;

    public Label engineDeliverySpeedLabel;
    public Label accessoriesDeliverySpeedLabel;
    public Label carAssemblySpeedLabel;

    public Slider bodyDeliverySpeedSlider;
    public Slider engineDeliverySpeedSlider;
    public Slider accessoriesDeliverySpeedSlider;
    public Slider carAssemblySpeedSlider;

    public Label bodyStorageQuantityLabel;
    public Label engineStorageQuantityLabel;
    public Label accessoriesStorageQuantityLabel;
    public Label carsStorageQuantityLabel;
    public Label carsManufacturedLabel;
    public Label carsSoldLabel;

    @FXML
    public void initialize() {
        bodyDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.bodyDeliveryTimeMs));
        engineDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.engineDeliveryTimeMs));
        accessoriesDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.accessoriesDeliveryTimeMs));
        carAssemblySpeedLabel.setText(convertTimeToStringSeconds(this.carAssemblyTimeMs));

        AnimationTimer frameRateMeter = new AnimationTimer() {
            @Override
            public void handle(long l) {
                bodyStorageQuantityLabel.setText(String.valueOf(getBodyStorageQuantity()));
                engineStorageQuantityLabel.setText(String.valueOf(getEngineStorageQuantity()));
                accessoriesStorageQuantityLabel.setText(String.valueOf(getAccessoriesStorageQuantity()));
                carsStorageQuantityLabel.setText(String.valueOf(getCarsStorageQuantity()));
                carsSoldLabel.setText(String.valueOf(getCarsSold()));
                carsManufacturedLabel.setText(String.valueOf(getCarsManufactured()));
            }
        };
        frameRateMeter.start();
    }


    public void setThreadPools(FactoryPool[] threadPools) {
        this.threadPools = threadPools;
    }

    public void startFactory() {
        if (factoryIsPaused && factoryIsRunning) {
            synchronized (pauseLock) {
                factoryIsPaused=false;
                pauseLock.notifyAll();
            }
        }
        else  {
            factoryIsRunning = true;
            for (FactoryPool threadPool : threadPools) {
                threadPool.startThreads();
            }
        }
    }

    public void pauseFactory() {
        factoryIsPaused = true;
    }

    public void stopFactory() {
        this.factoryIsRunning = false;
    }

    public void onStartFactoryButtonClicked() {
        if (!factoryIsPaused && factoryIsRunning){
            pauseFactory();
            startFactoryButton.setText("START");
        }
        else  {
            startFactory();
            startFactoryButton.setText("PAUSE");
        }
    }

    public void onResetButtonClicked( ) {
        resetCounters();
    }

    public boolean isFactoryRunning() {
        return factoryIsRunning;
    }

    public boolean isFactoryPaused() {
        return factoryIsPaused;
    }

    public Object getPauseLock() {
        return pauseLock;
    }

    public void onBodyDeliverySpeedSliderChanged() {
       this.bodyDeliveryTimeMs = (int)bodyDeliverySpeedSlider.getValue();
        bodyDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.bodyDeliveryTimeMs));
    }

    public void onEngineDeliverySpeedSliderChanged() {
        this.engineDeliveryTimeMs = (int)engineDeliverySpeedSlider.getValue();
        engineDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.engineDeliveryTimeMs));
    }

    public void onAccessoriesDeliverySpeedSliderChanged() {
        this.accessoriesDeliveryTimeMs = (int)accessoriesDeliverySpeedSlider.getValue();
        accessoriesDeliverySpeedLabel.setText(convertTimeToStringSeconds(this.accessoriesDeliveryTimeMs));
    }

    public void onCarAssemblySpeedSliderChanged() {
        this.carAssemblyTimeMs = (int)carAssemblySpeedSlider.getValue();
        carAssemblySpeedLabel.setText(convertTimeToStringSeconds(this.carAssemblyTimeMs));
    }

    public int getMinSaleTimeMs() {
        return minSaleTimeMs;
    }

    public int getMaxSaleTimeMs() {
        return maxSaleTimeMs;
    }

    public int getBodyStorageQuantity() {
        return bodyStorageQuantity;
    }

    public int getCarsStorageQuantity() {
        return carsStorageQuantity;
    }

    public int getEngineStorageQuantity() {
        return engineStorageQuantity;
    }

    public int getAccessoriesStorageQuantity() {
        return accessoriesStorageQuantity;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public synchronized void incrementBodyStorageQuantity() {
        bodyStorageQuantity++;
    }

    public synchronized void incrementEngineStorageQuantity() {
        engineStorageQuantity++;
    }

    public synchronized void incrementCarsStorageQuantity() {
        carsStorageQuantity++;
    }

    public synchronized void incrementAccessoriesStorageQuantity() {
        accessoriesStorageQuantity++;
    }

    public synchronized void incrementCarsSold() {
        carsSold++;
    }

    public synchronized void decrementBodyStorageQuantity() {
        bodyStorageQuantity--;
    }

    public synchronized void decrementEngineStorageQuantity() {
        engineStorageQuantity--;
    }

    public synchronized void decrementCarsStorageQuantity() {
        carsStorageQuantity--;
    }

    public synchronized void decrementAccessoriesStorageQuantity() {
        accessoriesStorageQuantity--;
    }

    public int getEngineDeliveryTimeMs() { return engineDeliveryTimeMs; }

    public int getAccessoriesDeliveryTimeMs() {
        return accessoriesDeliveryTimeMs;
    }

    public int getBodyDeliveryTimeMs() {
        return bodyDeliveryTimeMs;
    }

    public int getCarAssemblyTimeMs() {
        return carAssemblyTimeMs;
    }

    public int getCarsManufactured() {
        return carsManufactured;
    }

    public synchronized void incrementCarsManufactured() {
        carsManufactured++;
    }

    private String convertTimeToStringSeconds(int timeMs) {
        return timeMs / 1000.0 + "s";
    }

    private synchronized void resetCounters() {
        bodyStorageQuantity = 0;
        engineStorageQuantity = 0;
        accessoriesStorageQuantity = 0;
        carsStorageQuantity = 0;
        carsSold = 0;
        carsManufactured = 0;
    }
}