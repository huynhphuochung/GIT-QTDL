
module com.example.quantridulieu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.quantridulieu to javafx.fxml;
    exports com.example.quantridulieu;
}

