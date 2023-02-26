package cs255.Object;

import cs255.Vector;
import javafx.scene.paint.Color;

public class Sphere {

    private double radius;
    private double x;
    private double y;
    private double z;
    private double red;
    private double green;
    private double blue;
    private Vector centre;

    public Sphere(double sphereRadius, double sphereX, double sphereY, double sphereZ,
                  double redValue, double greenValue, double blueValue) {
        this.radius = sphereRadius;
        this.x = sphereX;
        this.y = sphereY;
        this.z = sphereZ;
        this.centre = new Vector(x, y, z);
        this.red = redValue;
        this.green = greenValue;
        this.blue = blueValue;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCentre() {
        this.centre = new Vector(x, y, z);
    }

    public Vector getCentre() {
        return centre;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        setCentre();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        setCentre();
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        setCentre();
    }

    public double checkForIntersection(Camera camera, Vector direction) {
        Vector vectorFromCentreToOrigin = camera.getPosition().sub(centre);

        double maxIntersection = Double.MAX_VALUE;
        double rayA = direction.dot(direction);
        double rayB = 2 * vectorFromCentreToOrigin.dot(direction);
        double rayC = vectorFromCentreToOrigin.dot(vectorFromCentreToOrigin) - radius * radius;
        double disc = rayB * rayB - 4 * rayA * rayC;

        // Check for intersection
        if (disc >= 0) {
            double solvedIntersection = (-rayB - Math.sqrt(disc)) / (2 * rayA);
            Vector pointOfIntersection = camera.getPosition().add(direction.mul(solvedIntersection));
            double distanceToIntersection = camera.getPosition().distance(pointOfIntersection);

            // Update the closest intersection and sphere
            if (distanceToIntersection < maxIntersection) {
                return distanceToIntersection;
            }
        }
        return maxIntersection;
    }

    public Color getColor(Camera camera, Vector direction, double closestIntersection, LightSource light) {
        Vector pointOfIntersection = camera.getPosition().add(direction.mul(closestIntersection));
        Vector temp = light.getPosition().sub(pointOfIntersection).normalize();
        Vector surfaceNormal = pointOfIntersection.sub(centre).normalize();

        // Set the ambient light color and intensity
        double ambientIntensity = 0.2;
        Color ambientLight = new Color(ambientIntensity, ambientIntensity, ambientIntensity, 1);

        // Calculate ambient shading
        double redValue = ambientLight.getRed() * red;
        double greenValue = ambientLight.getGreen() * green;
        double blueValue = ambientLight.getBlue() * blue;

        // Calculate diffuse shading
        double dotProduct = temp.dot(surfaceNormal);
        if (dotProduct > 0) {
            redValue += dotProduct * red;
            greenValue += dotProduct * green;
            blueValue += dotProduct * blue;
        }

        // Calculate specular shading
        double specularShininess = 50;
        Vector reflectionDirection = surfaceNormal.mul(2.0 * dotProduct).sub(temp);
        double specularIntensity = Math.pow(Math.max(reflectionDirection.dot(direction.neg()),
                0.0), specularShininess);

        return Color.color(inRange(redValue + specularIntensity), inRange(greenValue + specularIntensity),
                inRange(blueValue + specularIntensity), 1);
    }


    /**
     * This method ensures whatever value is passed cannot be less than -1
     * or greater than 1, good for ensuring no unexpected exceptions occur.
     *
     * @param value The value being observed
     * @return the value within -1 to +1 range
     */
    double inRange(double value) {
        return Math.min(Math.max(value, -1), 1);
    }

}