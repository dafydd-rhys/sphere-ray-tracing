package cs255.Object;

import cs255.Vector;

public class LightSource {

    private Vector origin;
    private double ambient;
    private double diffuse;
    private double specular;

    public LightSource(int positionX, int positionY, int positionZ, double ambient, double diffuse, double specular) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.origin = new Vector(positionX, positionY, positionZ);
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public double getAmbient() {
        return ambient;
    }

    public void setDiffuse(double diffuse) {
        this.diffuse = diffuse;
    }

    public double getDiffuse() {
        return diffuse;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    public double getSpecular() {
        return specular;
    }

    public void setPosition(Vector origin) {
        this.origin = origin;
    }

    public Vector getPosition() {
        return origin;
    }

}
