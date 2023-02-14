package cs255;

public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector() {
    }

    public Vector(double vectorX, double vectorY, double vectorZ) {
        x = vectorX;
        y = vectorY;
        z = vectorZ;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void normalise() {
        double magnitude = magnitude();
        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }
    }

    public double dot(Vector a) {
        return x * a.x + y * a.y + z * a.z;
    }

    public Vector sub(Vector a) {
        return new Vector(x - a.x, y - a.y, z - a.z);
    }

    public Vector add(Vector a) {
        return new Vector(x + a.x, y + a.y, z + a.z);
    }

    public Vector mul(double d) {
        return new Vector(d * x, d * y, d * z);
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

    public void print() {
        System.out.println("x = " + x + ", y = " + y + ", z = " + z);
    }
}

