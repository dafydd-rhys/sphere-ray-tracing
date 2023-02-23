package cs255.Object;

import cs255.Vector;

public class Sphere {

    private double radius;
    private double x;
    private double y;
    private double z;
    private double red;
    private double green;
    private double blue;

    public Sphere(double sphereRadius, double sphereX, double sphereY, double sphereZ,
                  double redValue, double greenValue, double blueValue) {
        this.radius = sphereRadius;
        this.x = sphereX;
        this.y = sphereY;
        this.z = sphereZ;
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

    public Vector getCentre() {
        return new Vector(x, y, z);
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
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}