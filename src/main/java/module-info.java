module com.example.actual {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.actual to javafx.fxml;
    opens model to javafx.fxml;

    exports com.example.actual;
    exports model;
    exports database;
    exports enums;
    exports exceptions;
    exports interfaces;
}