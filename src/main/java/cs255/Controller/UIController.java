package cs255.Controller;

import cs255.*;
import cs255.Object.Camera;
import cs255.Object.LightSource;
import cs255.Object.Sphere;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private Slider sphereXSlider;
    @FXML
    private Slider sphereYSlider;
    @FXML
    private Slider sphereZSlider;
    @FXML
    private Slider sphereRadius;
    @FXML
    private Slider cameraAzimuth;
    @FXML
    private Slider cameraAltitude;

    private final LightSource light = new LightSource(0, 0, -1500);
    private final ArrayList<Sphere> spheres = new ArrayList<>();
    private Camera camera;
    private Sphere selectedSphere;
    private final int MAX_RGB = 255;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spheres.add(new Sphere(150, 0, 0, 0, 1, 0, 0.5));
        spheres.add(new Sphere(75, 0, 0, 300, 1, 0, 0.5));
        spheres.add(new Sphere(50, 0, 0, -300, 1, 0.5, 0.5));
        //spheres.add(new Sphere(100, 200, 200, 0, 0, 1, 0));
        //spheres.add(new Sphere(100, -200, -200, -200, 0, 0, 1));

        backgroundSlider.maxProperty().setValue(spheres.size());
        if (spheres.size() > 0) selectedSphere = spheres.get(0);
        setSphereAttributes();

        //Creates an image we can write too
        int dimension = 1000;
        WritableImage environmentScene = new WritableImage(dimension, dimension);
        environment.setImage(environmentScene);
        initializeListeners(environmentScene);

        camera = new Camera(new Vector(0, 0, 0), new Vector(0, 0, 1),
                0, 0, 60);

        // Render the scene
        render(environmentScene);
    }

    public void render(WritableImage image) {
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                // Initialize the closest intersection distance and sphere to null
                double closestIntersection = Double.MAX_VALUE;
                Sphere closestSphere = null;

                // Calculate the direction vector for the current pixel
                Vector direction = new Vector(j - image.getWidth() / 2.0, i - image.getHeight() / 2.0,
                        image.getWidth() / (2.0 * Math.tan(Math.toRadians(camera.getFOV() / 2.0)))).normalize();
                direction = direction.rotateX(camera.getAltitude());
                direction = direction.rotateY(camera.getAzimuth());

                // Check each sphere for intersection
                for (Sphere sphere : spheres) {
                    Vector vectorFromCentreToOrigin = camera.getPosition().sub(sphere.getCentre());

                    double rayA = direction.dot(direction);
                    double rayB = 2 * vectorFromCentreToOrigin.dot(direction);
                    double rayC = vectorFromCentreToOrigin.dot(vectorFromCentreToOrigin) - sphere.getRadius() * sphere.getRadius();
                    double disc = rayB * rayB - 4 * rayA * rayC;

                    // Check for intersection
                    if (disc >= 0) {
                        double solvedIntersection = (-rayB - Math.sqrt(disc)) / (2 * rayA);
                        Vector pointOfIntersection = camera.getPosition().add(direction.mul(solvedIntersection));
                        double distanceToIntersection = camera.getPosition().distance(pointOfIntersection);

                        // Update the closest intersection and sphere
                        if (distanceToIntersection < closestIntersection) {
                            closestIntersection = distanceToIntersection;
                            closestSphere = sphere;
                        }
                    }
                }

                // If there was an intersection, calculate the color for the pixel
                if (closestSphere != null) {
                    Vector pointOfIntersection = camera.getPosition().add(direction.mul(closestIntersection));
                    Vector temp = light.getPosition().sub(pointOfIntersection).normalize();
                    Vector surfaceNormal = pointOfIntersection.sub(closestSphere.getCentre()).normalize();

                    // Set the ambient light color and intensity
                    double shininess = 50;
                    double intensity = 0.3;
                    Color ambientLight = new Color(intensity, intensity, intensity, 1);

                    // Calculate ambient shading
                    double red = ambientLight.getRed() * closestSphere.getRed();
                    double green = ambientLight.getGreen() * closestSphere.getGreen();
                    double blue =  ambientLight.getBlue() * closestSphere.getBlue();

                    // Calculate diffuse shading
                    double dotProduct = temp.dot(surfaceNormal);
                    if (dotProduct > 0) {
                        red += dotProduct * closestSphere.getRed();
                        green += dotProduct * closestSphere.getGreen();
                        blue += dotProduct * closestSphere.getBlue();
                    }

                    // Calculate specular shading
                    Vector reflectDir = surfaceNormal.mul(2.0 * dotProduct).sub(temp);
                    double specIntensity = Math.pow(Math.max(reflectDir.dot(direction.neg()), 0.0), shininess);

                    pixelWriter.setColor(j, i, Color.color(inRange(red + specIntensity),
                            inRange(green + specIntensity), inRange(blue + specIntensity), 1));
                } else {
                    // If there was no intersection, clear the pixel
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }
    }

    double inRange(double value) {
        return Math.min(Math.max(value, -1), 1);
    }

    private void setSphereAttributes() {
        Slider[] sliders = {backgroundSlider, redSlider, greenSlider, blueSlider, sphereXSlider, sphereYSlider,
                sphereZSlider, sphereRadius, cameraAltitude, cameraAzimuth};

        redSlider.setValue(selectedSphere.getRed() * MAX_RGB);
        greenSlider.setValue(selectedSphere.getGreen() * MAX_RGB);
        blueSlider.setValue(selectedSphere.getBlue() * MAX_RGB);
        sphereXSlider.setValue(selectedSphere.getX());
        sphereYSlider.setValue(selectedSphere.getY());
        sphereZSlider.setValue(selectedSphere.getZ());
        sphereRadius.setValue(selectedSphere.getRadius());

        for (Slider slider : sliders) {
            slider.setStyle("-fx-control-inner-background: #F6726D;");
        }
    }

    private void initializeListeners(WritableImage environmentScene) {
        backgroundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() % 1 == 0) {
                selectedSphere = spheres.get(newValue.intValue() - 1);
                setSphereAttributes();
            }
        });
        redSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setRed(redSlider.getValue() / MAX_RGB);
            render(environmentScene);
        });
        greenSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setGreen(greenSlider.getValue() / MAX_RGB);
            render(environmentScene);

        });
        blueSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setBlue(blueSlider.getValue() / MAX_RGB);
            render(environmentScene);
        });
        sphereXSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setX(sphereXSlider.getValue());
            render(environmentScene);

        });
        sphereYSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setY(sphereYSlider.getValue());
            render(environmentScene);
        });
        sphereZSlider.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setZ(sphereZSlider.getValue());
            render(environmentScene);
        });
        sphereRadius.valueProperty().addListener((obs, wasChanging, isNowChanging) -> {
            selectedSphere.setRadius(sphereRadius.getValue());
            render(environmentScene);
        });
        cameraAzimuth.valueProperty().addListener((obs, oldVal, newVal) -> {
            camera.setAzimuth(newVal.doubleValue());
            camera.setPosition(camera.calculateCameraPosition());
            render(environmentScene);
        });
        cameraAltitude.valueProperty().addListener((obs, oldVal, newVal) -> {
            camera.setAltitude(newVal.doubleValue());
            camera.setPosition(camera.calculateCameraPosition());
            render(environmentScene);
        });
    }

}
