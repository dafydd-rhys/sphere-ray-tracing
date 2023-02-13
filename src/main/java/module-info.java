module com.example.cs255 {
    requires javafx.controls;
    requires javafx.fxml;


    opens CS255 to javafx.fxml;
    exports CS255;
    exports CS255.Controller;
    opens CS255.Controller to javafx.fxml;
}