package cs255.Object;

import cs255.Vector;

public class Camera {

    private Vector position;
    private Vector direction;
    private double azimuth;
    private double altitude;
    private final double fov;

    public Camera(Vector position, Vector direction, double azimuth, double altitude, double fov) {
        this.position = position.add(direction.normalize().mul(-2000.0));
        this.direction = direction.normalize();
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
        updateDirection();
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        updateDirection();
    }

    public double getAltitude() {
        return altitude;
    }

    public double getFOV() {
        return fov;
    }

    private void updateDirection() {
        double cosAzimuth = Math.cos(Math.toRadians(azimuth));
        double sinAzimuth = Math.sin(Math.toRadians(azimuth));
        double cosAltitude = Math.cos(Math.toRadians(altitude));
        double sinAltitude = Math.sin(Math.toRadians(altitude));

        this.direction = new Vector(inRange(sinAzimuth * cosAltitude), inRange(sinAltitude),
                inRange(cosAzimuth * cosAltitude));
    }

    public Vector calculateCameraPosition() {
        double cosAzimuth = Math.cos(Math.toRadians(azimuth));
        double sinAzimuth = Math.sin(Math.toRadians(azimuth));
        double cosAltitude = Math.cos(Math.toRadians(altitude));
        double sinAltitude = Math.sin(Math.toRadians(altitude));

        return new Vector(inRange(sinAzimuth * cosAltitude), inRange(sinAltitude),
                inRange(cosAzimuth * cosAltitude)).mul(-2000);
    }

    double inRange(double value) {
        return Math.min(Math.max(value, -1), 1);
    }

}
