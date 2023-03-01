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

    /**
     * This method calculates the dot product of two vectors. The result is a scalar value
     * that indicates the extent of similarity or the projection of one vector onto another.
     *
     * @param v other vector
     * @return similarity value between the vectors
     */
    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * This method subtracts two vectors, every component x, y, z are subtracted
     * from both vectors.
     *
     * @param v vector to subtract
     * @return returns subtracted vectors result
     */
    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    /**
     * This method adds two vectors together, every component x, y, z are added
     * together from both vectors.
     *
     * @param v vector to add
     * @return returns added vector result
     */
    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    /**
     * This method multiplies a vector, every component x, y, z are multiplied
     * by passed value.
     *
     * @param value value to multiply vector by
     * @return returns multiplied vector
     */
    public Vector mul(double value) {
        return new Vector(value * x, value * y, value * z);
    }

    /**
     * This method converts a vector into its negated form.
     *
     * @return negated vector
     */
    public Vector neg() {
        return new Vector(-x, -y, -z);
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

    /**
     * normalizes the current vector to length of 1, each component x, y and z will add to 1
     * and the result represents the direction of the original vector
     *
     * @return normalized vector
     */
    public Vector normalize() {
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        if (magnitude == 0) {
            return new Vector(0, 0, 0);
        }

        return new Vector(x / magnitude, y / magnitude, z / magnitude);
    }

    /**
     * Gets distance from current vector to other vector.
     *
     * @param other other vector
     * @return the distance between vectors
     */
    public double distance(Vector other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * This method is used to rotate a vector around its x-axis,
     * the x value stays the same, the y and z values change based
     * on the angle of rotation.
     *
     * @param angle the angle of rotation
     * @return returns rotated vector
     */
    public Vector rotateX(double angle) {
        double cosTheta = Math.cos(Math.toRadians(angle));
        double sinTheta = Math.sin(Math.toRadians(angle));

        return new Vector(x, (y * cosTheta) - (z * sinTheta), (y * sinTheta) + (z * cosTheta));
    }

    /**
     * This method is used to rotate a vector around its y-axis,
     * the y value stays the same, the x and z values change based
     * on the angle of rotation.
     *
     * @param angle the angle of rotation
     * @return returns rotated vector
     */
    public Vector rotateY(double angle) {
        double cosTheta = Math.cos(Math.toRadians(angle));
        double sinTheta = Math.sin(Math.toRadians(angle));

        return new Vector((x * cosTheta) + (z * sinTheta), y, (-x * sinTheta) + (z * cosTheta));
    }

}


