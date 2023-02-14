package cs255.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import cs255.Vector;
import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    @FXML
    private ImageView environment;
    @FXML
    private Slider backgroundSlider;
    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;

    private final int width = 1920;
    private final int height = 1080;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //1. We create an image we can write to
        WritableImage environmentScene = new WritableImage(width, height);
        environment.setImage(environmentScene);

        backgroundSlider.valueProperty().addListener((observable, oldValue, newValue) -> Render(environmentScene));
        redSlider.valueProperty().addListener((observable, oldValue, newValue) -> Render(environmentScene));
        greenSlider.valueProperty().addListener((observable, oldValue, newValue) -> Render(environmentScene));
        blueSlider.valueProperty().addListener((observable, oldValue, newValue) -> Render(environmentScene));

        environment.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });
        Render(environmentScene);
    }

    public void Render(WritableImage image) {
        PixelWriter pixelWriter = image.getPixelWriter();

        Vector colourVector = new Vector(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());

        //for each pixel in image
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Vector rayOrigin = new Vector(j - (float) (width / 2), i - (float) height / 2, -500);
                Vector rayDirection = new Vector(0, 0, 1);

                double radiusOfSphere = 100;
                Vector centreOfSphere = new Vector(0, 0, 500);
                Vector lightSourceOrigin = new Vector(250, 250, -200);

                Vector vectorFromCentreToOrigin = rayOrigin.sub(centreOfSphere);

                double rayA = rayDirection.dot(rayDirection);
                double rayB = 2 * vectorFromCentreToOrigin.dot(rayDirection);
                double rayC = vectorFromCentreToOrigin.dot(vectorFromCentreToOrigin) - radiusOfSphere * radiusOfSphere;

                double disc = rayB * rayB - 4 * rayA * rayC;
                double solvedIntersection = (-rayB - Math.sqrt(disc)) / 2 * rayA;

                Vector pointOfIntersection = rayOrigin.add(rayDirection.mul(solvedIntersection));
                Vector newLightSource = lightSourceOrigin.sub(pointOfIntersection);
                Vector surfaceNormal = pointOfIntersection.sub(centreOfSphere);

                newLightSource.normalise();
                surfaceNormal.normalise();

                double colour;
                double dotProduct = newLightSource.dot(surfaceNormal);
                if (dotProduct < 0) {
                    colour = 0;
                } else {
                    colour = dotProduct;
                }
                pixelWriter.setColor(j, i, Color.color(colour * colourVector.getX(),
                        colour * colourVector.getY(), colour * colourVector.getZ(), 1));
            }
        }
    }

}