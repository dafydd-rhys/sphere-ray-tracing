module com.example.cs255 {
    requires javafx.controls;
    requires javafx.fxml;

    opens cs255 to javafx.fxml;
    exports cs255;
    exports cs255.Controller;
    opens cs255.Controller to javafx.fxml;
}