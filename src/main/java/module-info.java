
module com.example.quantridulieu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires io;

    opens com.example.quantridulieu to javafx.fxml;
    exports com.example.quantridulieu;
}

