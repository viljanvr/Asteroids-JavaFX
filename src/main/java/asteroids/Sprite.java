package asteroids;

import java.util.Collection;

public abstract class Sprite {

    protected double x1, y1, x2, y2, rotation;
    protected Vector velocity = new Vector(0, 0);
    private final int IMAGEWIDTH, IMAGEHEIGHT;
    private final String IMAGEURL;

    public Sprite(double x1, double y1, double speed, double speedDirection, int imageWidth, int imageHeight, String IMAGEURL) {
        this.IMAGEURL = IMAGEURL;
        this.IMAGEWIDTH = imageWidth;
        this.IMAGEHEIGHT = imageHeight;

        getVelocity().setLength(speed);
        getVelocity().setAngle(speedDirection);
        setPosX(x1);
        setPosY(y1);
    }

    public abstract Boolean checkCollision(Collection<Sprite> list);

    public void setPosX(double x1) {
        this.x1 = x1;
        x2 = x1 + IMAGEWIDTH;
    }

    public void setPosY(double y1) {
        this.y1 = y1;
        y2 = y1 + IMAGEHEIGHT;
    }

    public boolean containsSprite(Sprite sprite) {
        return (x1 < sprite.x2 && sprite.x1 < x2) ? (y1 < sprite.y2 && sprite.y1 < y2) : false;
    }

    public boolean isInsideRectangle(int x1, int y1, int x2, int y2) {
        return ((this.x1 > x1 && this.x1 < x2 || this.x2 > x1 && this.x2 < x2) && 
        (this.y1 > y1 && this.y1 < y2 || this.y2 > y1 && this.y2 < y2));

    }

    public void updatePosition() {
        x1 += velocity.getX();
        x2 = x1 + IMAGEWIDTH;
        y1 += velocity.getY();
        y2 = y1 + IMAGEHEIGHT;
    }

    protected void wrap() {
        if (x1 > AsteroidsController.CANVASWIDTH) {
            x1 -= AsteroidsController.CANVASWIDTH + 64;
            x2 -= AsteroidsController.CANVASWIDTH + 64;
        }
        if (x2 < 0) {
            x1 += AsteroidsController.CANVASWIDTH + 64;
            x2 += AsteroidsController.CANVASWIDTH + 64;
        }
        if (y1 > AsteroidsController.CANVASHEIGHT) {
            y1 -= AsteroidsController.CANVASHEIGHT + 64;
            y2 -= AsteroidsController.CANVASHEIGHT + 64;
        }
        if (y2 < 0) {
            y1 += AsteroidsController.CANVASHEIGHT + 64;
            y2 += AsteroidsController.CANVASHEIGHT + 64;
        }
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

    public double getRotation() {
        return rotation;
    }

    public String getImageURL() {
        return IMAGEURL;
    }

    public int getImageWidth() {
        return IMAGEWIDTH;
    }

    public int getImageHeight() {
        return IMAGEHEIGHT;
    }
}
