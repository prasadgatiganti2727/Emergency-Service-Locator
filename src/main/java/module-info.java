module com.example.emergency_service {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    opens com.example.emergency_service to javafx.fxml;
    exports com.example.emergency_service;
    exports com.example.emergency_service.Controller;
    opens com.example.emergency_service.Controller to javafx.fxml;
}