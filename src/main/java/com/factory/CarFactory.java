package com.factory;

import com.factory.model.Storage;
import com.factory.model.entities.Accessories;
import com.factory.model.entities.Body;
import com.factory.model.entities.Car;
import com.factory.model.entities.Engine;
import com.factory.model.pools.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;


public class CarFactory extends Application {

    public static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage) throws IOException {
        File configFile = new File("config.txt");
        ConfigParser configParser = new ConfigParser(configFile);
        configParser.getConfig();
        configParser.map.get("numberOfDealers");

        if (configParser.map.get("isLogging") == 0) {
            LogManager.shutdown();
        }

        Storage<Engine> engineStorage = new Storage<>(configParser.map.get("engineStorageCapacity"));
        Storage<Body> bodyStorage = new Storage<>(configParser.map.get("bodyStorageCapacity"));
        Storage<Accessories> accessoriesStorage = new Storage<>(configParser.map.get("accessoriesStorageCapacity"));
        Storage<Car> carStorage = new Storage<>(configParser.map.get("carStorageCapacity"));

        FXMLLoader fxmlLoader = new FXMLLoader(CarFactory.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Controller controller = fxmlLoader.getController();
        stage.setTitle("Kirill Ponomarev | Car Factory");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        EngineSupplierPool engineSupplierPool = new EngineSupplierPool(controller, engineStorage, configParser.map.get("numberOfEngineSuppliers"));
        BodySupplierPool bodySupplierPool = new BodySupplierPool(controller, bodyStorage, configParser.map.get("numberOfBodySuppliers"));
        AccessoriesSupplierPool accessoriesSupplierPool = new AccessoriesSupplierPool(controller, accessoriesStorage, configParser.map.get("numberOfAccessoriesSuppliers"));
        AssemblersPool assemblersPool = new AssemblersPool(controller, carStorage, engineStorage, bodyStorage, accessoriesStorage, configParser.map.get("numberOfAssemblers"));
        DealersPool dealersPool = new DealersPool(controller, carStorage, configParser.map.get("numberOfDealers"));

        FactoryPool[] threadPools = {engineSupplierPool, bodySupplierPool, accessoriesSupplierPool, assemblersPool, dealersPool};
        controller.setThreadPools(threadPools);


        stage.setOnCloseRequest(event -> {
            controller.stopFactory();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        CarFactory.launch();
    }
}