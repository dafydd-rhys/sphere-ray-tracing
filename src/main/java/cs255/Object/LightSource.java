package cs255.Object;

import cs255.Vector;

public class LightSource {

    private final Vector origin;
    private final Vector direction = new Vector(0, 0, 1);

    public LightSource(int positionX, int positionY, int positionZ) {
        origin = new Vector(positionX, positionY, positionZ);
    }

    public Vector getPosition() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

}
