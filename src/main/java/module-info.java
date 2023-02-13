module com.example.cs255 {
    requires javafx.controls;
    requires javafx.fxml;


    opens sphere_image_manipulation to javafx.fxml;
    exports sphere_image_manipulation;
    exports sphere_image_manipulation.Controller;
    opens sphere_image_manipulation.Controller to javafx.fxml;
}