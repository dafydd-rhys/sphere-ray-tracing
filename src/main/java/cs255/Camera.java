package cs255;

public class Camera {

    private Vector position;
    private Vector direction;

    public Camera(Vector position, Vector direction) {
        this.position = position;
        this.direction = direction;
    }

    public void setCameraPosition(Vector cameraPosition) {
        this.position = cameraPosition;
    }

    public Vector getCameraPosition() {
        return position;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

}
