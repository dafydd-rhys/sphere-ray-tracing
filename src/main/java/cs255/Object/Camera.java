package cs255.Object;

import cs255.Vector;

public class Camera {

    private double fov;
    private Vector position;
    private Vector direction;
    private double azimuth;
    private double altitude;

    public Camera(Vector position, Vector direction, double azimuth, double altitude, double fov) {
        this.position = position;
        this.direction = direction;
        this.azimuth = azimuth;
        this.altitude = altitude;
        this.fov = fov;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
        updateCamera();
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        updateCamera();
    }

    public double getAltitude() {
        return altitude;
    }

    public void setFOV(double fov) {
        this.fov = fov;
    }

    public double getFOV() {
        return fov;
    }

    /**
     * Updates the camera position and direction based on the
     * current azimuth and altitude.
     */
    private void updateCamera() {
        double cosAzimuth = Math.cos(Math.toRadians(azimuth));
        double sinAzimuth = Math.sin(Math.toRadians(azimuth));
        double cosAltitude = Math.cos(Math.toRadians(-altitude));
        double sinAltitude = Math.sin(Math.toRadians(-altitude));

        Vector updatedVector =  new Vector(inRange(sinAzimuth * cosAltitude), inRange(sinAltitude),
                inRange(cosAzimuth * cosAltitude));

        this.direction = updatedVector;
        this.position = updatedVector.mul(-2000);
    }

    /**
     * ensures that the passed value is between -1 an 1.
     *
     * @param value passed value
     * @return returns the value between -1 and 1
     */
    double inRange(double value) {
        return Math.min(Math.max(value, -1), 1);
    }

}
