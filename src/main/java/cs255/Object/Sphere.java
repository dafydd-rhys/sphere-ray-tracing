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

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getCentre() {
        return new Vector(x, y, z);
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getRed() {
        return red;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getBlue() {
        return blue;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getGreen() {
        return green;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

}