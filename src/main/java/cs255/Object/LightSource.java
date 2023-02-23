package cs255.Object;

import cs255.Vector;

public class LightSource {

    private final Vector origin;

    public LightSource(int positionX, int positionY, int positionZ) {
        origin = new Vector(positionX, positionY, positionZ);
    }

    public Vector getPosition() {
        return origin;
    }

}
