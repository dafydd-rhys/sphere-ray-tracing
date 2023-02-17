package cs255.Controller;

import cs255.Camera;
import cs255.LightSource;
import cs255.Sphere;
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

    private Camera camera;
    private final LightSource light = new LightSource(0, 0, 0);
    private final ArrayList<Sphere> spheres = new ArrayList<>();
    private Sphere selectedSphere;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spheres.add(new Sphere(100, 0, 0, -250, 1, 0, 0));
        spheres.add(new Sphere(100, 10, 30, 0, 0, 1, 0));
        spheres.add(new Sphere(100, 20, 60, 250, 0, 0, 1));
        backgroundSlider.maxProperty().setValue(spheres.size());
        selectedSphere = spheres.get(0);
        setSphereAttributes();

        //Creates an image we can write too
        int dimension = 1000;
        WritableImage environmentScene = new WritableImage(dimension, dimension);
        environment.setImage(environmentScene);
        initializeListeners(environmentScene);

        Vector cameraPosition = new Vector(0, 0, -500);
        Vector cameraDirection = new Vector(0, 0, 1);
        camera = new Camera(cameraPosition, cameraDirection);
        updateCamera();

        // Render the scene
        render(environmentScene);
    }

    public void render(WritableImage image) {
        debug();
        PixelWriter pixelWriter = image.getPixelWriter();

        // Set the ambient light color and intensity
        Color ambientLight = new Color(0.2, 0.2, 0.2, 1.0);
        double shininess = 50;

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                // Initialize the closest intersection distance and sphere to null
                double closestIntersection = Double.MAX_VALUE;
                Sphere closestSphere = null;
                Vector cameraRelative = new Vector(j, i, -500);

                // Check each sphere for intersection
                for (Sphere sphere : spheres) {
                    Vector vectorFromCentreToOrigin = cameraRelative.sub(sphere.getCentre());

                    double rayA = camera.getDirection().dot(camera.getDirection());
                    double rayB = 2 * vectorFromCentreToOrigin.dot(camera.getDirection());
                    double rayC = vectorFromCentreToOrigin.dot(vectorFromCentreToOrigin) -
                            sphere.getRadius() * sphere.getRadius();

                    double disc = rayB * rayB - 4 * rayA * rayC;

                    // Check for intersection
                    if (disc >= 0) {
                        double solvedIntersection = (-rayB - Math.sqrt(disc)) / (2 * rayA);
                        Vector pointOfIntersection = cameraRelative.add(camera.getDirection().mul(solvedIntersection));
                        double distanceToIntersection = cameraRelative.distance(pointOfIntersection);

                        // Update the closest intersection and sphere
                        if (distanceToIntersection < closestIntersection) {
                            closestIntersection = distanceToIntersection;
                            closestSphere = sphere;
                        }
                    }
                }

                // If there was an intersection, calculate the color for the pixel
                if (closestSphere != null) {
                    Vector pointOfIntersection = cameraRelative.add(camera.getDirection().mul(closestIntersection));
                    Vector temp = light.getPosition().sub(pointOfIntersection).normalize();
                    Vector surfaceNormal = pointOfIntersection.sub(closestSphere.getCentre()).normalize();

                    // Calculate ambient shading
                    double red = ambientLight.getRed() * closestSphere.getRed();
                    double green = ambientLight.getGreen() * closestSphere.getGreen();
                    double blue = ambientLight.getBlue() * closestSphere.getBlue();

                    // Calculate diffuse shading
                    double dotProduct = temp.dot(surfaceNormal);
                    if (dotProduct > 0) {
                        red += dotProduct * closestSphere.getRed();
                        green += dotProduct * closestSphere.getGreen();
                        blue += dotProduct * closestSphere.getBlue();
                    }

                    // Calculate specular shading
                    Vector reflectDir = surfaceNormal.mul(2.0 * dotProduct).sub(temp);
                    double specIntensity = Math.pow(Math.max(reflectDir.dot(camera.getDirection().neg()), 0.0), shininess);
                    red += specIntensity;
                    green += specIntensity;
                    blue += specIntensity;

                    if (red > 1) red = 1;
                    if (green > 1) green = 1;
                    if (blue > 1) blue = 1;

                    pixelWriter.setColor(j, i, Color.color(red, green, blue, 1));
                } else {
                    // If there was no intersection, clear the pixel
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }
    }

    private void updateCamera() {
        double theta = Math.toRadians(cameraAzimuth.getValue());
        double phi = Math.toRadians(cameraAltitude.getValue());

        double x = Math.sin(theta) * Math.cos(phi);
        double y = Math.sin(phi);
        double z = Math.cos(theta) * Math.cos(phi);

        camera.setDirection(new Vector(x, y, z).neg());
        camera.setCameraPosition(new Vector(x, y, z).mul(500));
    }

    private void setSphereAttributes() {
        redSlider.setValue(selectedSphere.getRed() * 255);
        greenSlider.setValue(selectedSphere.getGreen() * 255);
        blueSlider.setValue(selectedSphere.getBlue() * 255);
        sphereXSlider.setValue(selectedSphere.getX() - 500);
        sphereYSlider.setValue(selectedSphere.getY() - 500);
        sphereZSlider.setValue(selectedSphere.getZ() + 500);
        sphereRadius.setValue(selectedSphere.getRadius());
    }

    private void initializeListeners(WritableImage environmentScene) {
        backgroundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() % 1 == 0) {
                selectedSphere = spheres.get(newValue.intValue() - 1);
                setSphereAttributes();

                render(environmentScene);
            }
        });
        redSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setRed(redSlider.getValue() / 255);
                render(environmentScene);
            }
        });
        greenSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setGreen(greenSlider.getValue() / 255);
                System.out.println(selectedSphere.getX() + " " + selectedSphere.getY() + " " + selectedSphere.getZ());
                render(environmentScene);
            }
        });
        blueSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setBlue(blueSlider.getValue() / 255);
                render(environmentScene);
            }
        });
        sphereXSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setX(sphereXSlider.getValue());
                render(environmentScene);
            }
        });
        sphereYSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setY(sphereYSlider.getValue());
                render(environmentScene);
            }
        });
        sphereZSlider.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setZ(sphereZSlider.getValue());
                render(environmentScene);
            }
        });
        sphereRadius.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                selectedSphere.setRadius(sphereRadius.getValue());
                render(environmentScene);
            }
        });
        cameraAzimuth.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                updateCamera();
                render(environmentScene);
            }
        });
        cameraAltitude.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                updateCamera();
                render(environmentScene);
            }
        });
    }

    private void debug() {
        System.out.println("Camera Position: " + camera.getCameraPosition().getX() + ", " +
        camera.getCameraPosition().getY() + ", " + camera.getCameraPosition().getZ());
        System.out.println("Camera Direction: " + camera.getDirection().getX() + ", "
         + camera.getDirection().getY() + ", " + camera.getDirection().getZ());
    }

}
