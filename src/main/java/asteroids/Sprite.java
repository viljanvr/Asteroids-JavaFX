package asteroids;

import java.util.Collection;

public abstract class Sprite {

    protected double x1, y1, x2, y2, rotation;
    protected Vector velocity;
    private int imageWidth, imageHeight;
    private String imageURL;

    public Sprite(double x1, double y1, int imageWidth, int imageHeight, String imagePath) {
        this.imageURL = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        setPosX(x1);
        setPosY(y1);

        rotation = 0;
        velocity = new Vector(0, 0);
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

    public void setPosX(double x1) {
        this.x1 = x1;
        this.x2 = x1 + imageWidth;
    }

    public void setPosY(double y1) {
        this.y1 = y1;
        this.y2 = y1 + imageWidth;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setImageSize(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public double getRotation() {
        return rotation;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
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

    // not currently used but could be for spaceship spawn
    public boolean contains(int x1, int y1) {
        if (x1 > this.x1 && x1 < this.x2 && y1 > this.y1 && y1 < this.y2)
            return true;
        return false;
    }

    public abstract Boolean checkCollision(Collection<Sprite> list);

    public void updatePosition() {
        x1 += velocity.getX();
        x2 += velocity.getX();
        y1 += velocity.getY();
        y2 += velocity.getY();
    }

    public void wrap() {
        if (x1 > AsteroidsController.CanvasWidth) {
            x1 -= AsteroidsController.CanvasWidth + 64;
            x2 -= AsteroidsController.CanvasWidth + 64;
        }
        if (x2 < 0) {
            x1 += AsteroidsController.CanvasWidth + 64;
            x2 += AsteroidsController.CanvasWidth + 64;
        }
        if (y1 > AsteroidsController.CanvasHeight) {
            y1 -= AsteroidsController.CanvasHeight + 64;
            y2 -= AsteroidsController.CanvasHeight + 64;
        }
        if (y2 < 0) {
            y1 += AsteroidsController.CanvasHeight + 64;
            y2 += AsteroidsController.CanvasHeight + 64;
        }
    }
}
