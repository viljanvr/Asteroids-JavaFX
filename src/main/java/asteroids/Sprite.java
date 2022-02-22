package asteroids;

import java.lang.Math;

public class Sprite {

    private int x1, y1, x2, y2;
    private double rotation;
    private Vector velocity;
    private String imageURL;
    private int imageWidth = 0;
    private int imageHeight = 0;

    public Sprite(int x1, int y1, String imagePath) {
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

    public Sprite(int x1, int y1, Vector velocity, String imagePath){
        this(x1, y1, imagePath);
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

    public void setRotation(double angle){
        this.rotation = angle;
    }

    public double getRotation(){
        return this.rotation;
    }

    public void setImageSize(int width, int height){
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public int getImageWidth(){
        return this.imageWidth;
    }

    public int getImageHeight(){
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
        x2 += velocity.getX();
        y1 += velocity.getY();
        y2 += velocity.getY();
    }

    public static void main(String[] args) {

    }

}
