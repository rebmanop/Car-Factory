module com.factory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.apache.logging.log4j;

    exports com.factory;
    opens com.factory to javafx.fxml;
    exports com.factory.model;
    opens com.factory.model to javafx.fxml;
    exports com.factory.model.entities;
    opens com.factory.model.entities to javafx.fxml;
    exports com.factory.model.suppliers;
    opens com.factory.model.suppliers to javafx.fxml;
}