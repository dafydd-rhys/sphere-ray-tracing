package cs255;

public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector() {
    }

    public Vector(double vectorX, double vectorY, double vectorZ) {
        this.x = vectorX;
        this.y = vectorY;
        this.z = vectorZ;
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

    public Vector neg() {
        return new Vector(-x, -y, -z);
    }

    public Vector cross(Vector v2) {
        return new Vector(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
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

    public Vector normalize() {
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        if (magnitude == 0) {
            return new Vector(0, 0, 0);
        }

        return new Vector(x / magnitude, y / magnitude, z / magnitude);
    }

    public double distance(Vector other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vector rotateX(double angle) {
        double cosTheta = Math.cos(Math.toRadians(angle));
        double sinTheta = Math.sin(Math.toRadians(angle));

        return new Vector(x, (y * cosTheta) - (z * sinTheta), (y * sinTheta) + (z * cosTheta));
    }

    public Vector rotateY(double angle) {
        double cosTheta = Math.cos(Math.toRadians(angle));
        double sinTheta = Math.sin(Math.toRadians(angle));

        return new Vector((x * cosTheta) + (z * sinTheta), y, (-x * sinTheta) + (z * cosTheta));
    }

}


