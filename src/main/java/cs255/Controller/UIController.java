package cs255.Controller;

import cs255.Object.Camera;
import cs255.Object.LightSource;
import cs255.Object.Sphere;
import cs255.Vector;
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
    @FXML
    private Slider cameraFOV;

    /**
     * Maximum value in r,g,b scale.
     */
    private final int MAX_RGB = 255;
    /**
     * Camera object.
     */
    private Camera camera;
    /**
     * Light source object.
     */
    private LightSource light;
    /**
     * List of spheres in the scene.
     */
    private final ArrayList<Sphere> spheres = new ArrayList<>();
    /**
     * The focused sphere.
     */
    private Sphere selectedSphere;

    /**
     * This method is run whenever the FXML is initialized, it creates all objects required
     * for the scene and selects a sphere to be focused on. Also, styling is added to each slider.
     *
     * @param url            URL of the FXML
     * @param resourceBundle The resource bundle attached to the FXML
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create spheres, add them to array list
        spheres.add(new Sphere(150, 0, 0, 0, 1, 0, 0.5));
        spheres.add(new Sphere(50, 0, 100, -300, 1, 0.5, 0.5));
        spheres.add(new Sphere(100, 200, 200, 0, 0, 1, 0));
        spheres.add(new Sphere(100, -200, -200, -200, 0, 0, 1));

        //sets initial sphere
        backgroundSlider.maxProperty().setValue(spheres.size());
        if (spheres.size() > 0) {
            selectedSphere = spheres.get(0);
        }

        //adds styling to each slider
        Slider[] sliders = {backgroundSlider, redSlider, greenSlider, blueSlider, sphereXSlider, sphereYSlider,
                sphereZSlider, sphereRadius, cameraAltitude, cameraAzimuth, cameraFOV};
        for (Slider slider : sliders) {
            slider.setStyle("-fx-control-inner-background: #F6726D;");
        }

        setSphereAttributes();

        //Creates an image we can write too
        int dimension = 1000;
        WritableImage environmentScene = new WritableImage(dimension, dimension);
        environment.setImage(environmentScene);
        initializeListeners(environmentScene);

        Vector cameraPosition = new Vector(0, 0, -2000);
        Vector cameraDirection = new Vector(0, 0, 1);
        double cameraFOV = 60;

        //Initialize camera and light source
        light = new LightSource(-400, 0, -2000);
        camera = new Camera(cameraPosition, cameraDirection, 0, 0, cameraFOV);

        // Render the scene
        render(environmentScene);
    }

    /**
     * Sets all the relevant slider values to that of the focused sphere.
     */
    private void setSphereAttributes() {
        redSlider.setValue(selectedSphere.getRed() * MAX_RGB);
        greenSlider.setValue(selectedSphere.getGreen() * MAX_RGB);
        blueSlider.setValue(selectedSphere.getBlue() * MAX_RGB);
        sphereXSlider.setValue(selectedSphere.getX());
        sphereYSlider.setValue(selectedSphere.getY());
        sphereZSlider.setValue(selectedSphere.getZ());
        sphereRadius.setValue(selectedSphere.getRadius());
    }

    /**
     * This method renders the UI, it uses the camera position, light position and sphere
     * positions to calculate where they should be in the scene and also implements
     * and calculates different light shadings on the objects. It uses camera rays to
     * detect intersections with spheres etc. to calculate
     *
     * @param image the UI image being used to render the scene
     */
    public void render(WritableImage image) {
        PixelWriter pixelWriter = image.getPixelWriter();

        //loops through each pixel of image
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
                    double distanceToIntersection = sphere.checkForIntersection(camera, direction);

                    // Update the closest intersection and sphere
                    if (distanceToIntersection < closestIntersection) {
                        closestIntersection = distanceToIntersection;
                        closestSphere = sphere;
                    }
                }

                // If there was an intersection, calculate the color for the pixel, if not, draw pixel black.
                if (closestSphere != null) {
                    pixelWriter.setColor(j, i, closestSphere.getColor(camera, direction, closestIntersection, light));
                } else {
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }
    }

    /**
     * This method initializes all the UI elements to begin listening for user
     * input, whenever a slider value is changed the code within each unique
     * slider is run and updates the UI.
     *
     * @param environmentScene The scene that is being updated
     */
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
            render(environmentScene);
        });
        cameraAltitude.valueProperty().addListener((obs, oldVal, newVal) -> {
            camera.setAltitude(newVal.doubleValue());
            render(environmentScene);
        });
        cameraFOV.valueProperty().addListener((obs, oldVal, newVal) -> {
            camera.setFOV(newVal.doubleValue());
            render(environmentScene);
        });
    }

}
