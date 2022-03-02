package asteroids;

import java.lang.Math;

public class Sprite {

    protected double x1, y1, x2, y2;
    protected double rotation;
    protected Vector velocity;
    private String imageURL;
    private int imageWidth;
    private int imageHeight;

    public Sprite(double x1, double y1, int imageHeight, int imageWidth, String imagePath) {

        if (x1 < 0 || y1 < 0) {
            throw new IllegalArgumentException("Innvalid inputs for rectangle");
        }
        this.imageURL = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        this.x1 = x1;
        this.x2 = x1 + imageWidth;
        this.y1 = y1;
        this.y2 = y1 + imageHeight;

        this.rotation = 0;
        this.velocity = new Vector(0, 0);


    }

    public Sprite(double x1, double y1, int imageHeight, int imageWidth, Vector velocity, String imagePath) {

        this(x1, y1, imageHeight, imageWidth, imagePath);
        this.velocity = velocity;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getPosX() {
        return x1;
    }

    public double getPosY() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getRotation() {
        return this.rotation;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    public boolean contains(double x, double y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public boolean contains(Sprite sprite) {
        if (x1 < sprite.x2 && sprite.x1 < x2) {
            if (y1 < sprite.y2 && sprite.y1 < y2)
                return true;
            else
                return false;
        } else
            return false;
    }

    public void updatePosition() {
        x1 += velocity.getX();
        x2 += velocity.getX();
        y1 += velocity.getY();
        y2 += velocity.getY();
    }

    public void wrap(){
        if (x1 > 800) {
            x1 -= 864;
            x2 -= 864;
        }
        if (x2 < 0){
            x1 += 864;
            x2 += 864;
        }
        if (y1 > 600){
            y1 -= 664;
            y2 -= 664;
        }
        if (y2 < 0){
            y1 += 664;
            y2 += 664;
        }
    }

    public static void main(String[] args) {

    }

}
