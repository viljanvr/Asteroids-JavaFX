package asteroids;

import java.lang.Math;

public class Sprite {

    protected int x1, y1, x2, y2;
    protected double rotation;
    protected Vector velocity;
    private String imageURL;
    private int imageWidth;
    private int imageHeight;

    public Sprite(int x1, int y1, int imageHeight, int imageWidth, String imagePath) {
        if (x1 < 0 || y1 < 0) {
            throw new IllegalArgumentException("Innvalid inputs for rectangle");
        }
        this.imageURL = imagePath;

        this.x1 = x1;
        x2 = x1 + imageWidth;
        this.y1 = y1;
        y2 = y2 + imageHeight;

        rotation = 0;
        velocity = new Vector(0, 0);

    }

    public Sprite(int x1, int y1, int imageHeight, int imageWidth, Vector velocity, String imagePath) {
        this(x1, y1, imageHeight, imageWidth, imagePath);
        this.velocity = velocity;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public int getPosX() {
        return x1;
    }

    public int getPosY() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void rotate(double angle) {
        rotation = Math.acos(Math.cos(getRotation() + angle));
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

    public boolean contains(int x, int y) {
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
        if (x1 > 800)
            x1 -= 864;
        x2 += velocity.getX();
        if (x2 > 800)
            x2 -= 800;
        y1 += velocity.getY();
        if (y1 > 600)
            y1 -= 664;
        y2 += velocity.getY();
        if (y2 > 600)
            y2 -= 600;
    }

    public static void main(String[] args) {

    }

}
