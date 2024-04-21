module com.fxm.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fxm.demo to javafx.fxml;
    exports com.fxm.demo;
}