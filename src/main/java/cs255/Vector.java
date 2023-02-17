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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector neg() {
        return new Vector(-x, -y, -z);
    }

    public Vector normalize() {
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        if (magnitude == 0) {
            return new Vector(0, 0, 0);
        }
        double newX = x / magnitude;
        double newY = y / magnitude;
        double newZ = z / magnitude;

        return new Vector(newX, newY, newZ);
    }

    public double distance(Vector other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

}

