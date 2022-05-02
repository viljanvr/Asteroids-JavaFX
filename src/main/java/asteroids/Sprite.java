package asteroids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class Sprite {

    protected double x1, y1, x2, y2;
    protected Vector velocity = new Vector(0, 0);
    private final int IMAGEWIDTH, IMAGEHEIGHT;
    private final String IMAGE_URL;

    public Sprite(double x1, double y1, double speed, double speedDirection, int IMAGEWIDTH, int IMAGEHEIGHT,
            String IMAGE_URL) {
        checkValidNumbers(-55, AsteroidsController.CANVASWIDTH, "innvalid x coordinate input", x1);
        checkValidNumbers(-55, AsteroidsController.CANVASHEIGHT, "innvalid y coordinate input", y1);
        checkValidNumbers(0, 55, "innvalid image height or width input", (double) IMAGEHEIGHT, (double) IMAGEWIDTH);

        this.IMAGE_URL = IMAGE_URL;
        this.IMAGEWIDTH = IMAGEWIDTH;
        this.IMAGEHEIGHT = IMAGEHEIGHT;

        setPosXY(x1, y1);

        getVelocity().setLength(speed);
        getVelocity().setAngle(speedDirection);
    }

    public void setPosXY(double x1, double y1) {
        this.x1 = x1;
        x2 = x1 + IMAGEWIDTH;
        this.y1 = y1;
        y2 = y1 + IMAGEHEIGHT;
    }

    public abstract boolean checkCollision(Collection<Sprite> list);

    public boolean overlapsSprite(Sprite sprite) {
        return sprite.isInsideRectangle(x1, y1, x2, y2);
    }

    public boolean isInsideRectangle(double x1, double y1, double x2, double y2) {
        return (this.x1 < x2 && x1 < this.x2) && (this.y1 < y2 && y1 < this.y2);
    }

    public void updatePosition() {
        setPosXY(x1 + velocity.getX(), y1 + velocity.getY());
    }

    protected void wrap() {
        if (x1 > AsteroidsController.CANVASWIDTH) {
            x1 -= AsteroidsController.CANVASWIDTH + IMAGEWIDTH;
            x2 -= AsteroidsController.CANVASWIDTH + IMAGEWIDTH;
        } else if (x2 < 0) {
            x1 += AsteroidsController.CANVASWIDTH + IMAGEWIDTH;
            x2 += AsteroidsController.CANVASWIDTH + IMAGEWIDTH;
        } else if (y1 > AsteroidsController.CANVASHEIGHT) {
            y1 -= AsteroidsController.CANVASHEIGHT + IMAGEHEIGHT;
            y2 -= AsteroidsController.CANVASHEIGHT + IMAGEHEIGHT;
        } else if (y2 < 0) {
            y1 += AsteroidsController.CANVASHEIGHT + IMAGEHEIGHT;
            y2 += AsteroidsController.CANVASHEIGHT + IMAGEHEIGHT;
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

    public String getImageURL() {
        return IMAGE_URL;
    }

    public int getImageWidth() {
        return IMAGEWIDTH;
    }

    public int getImageHeight() {
        return IMAGEHEIGHT;
    }

    private void checkValidNumbers(int min, int max, String info, Double... numbers) {
        List<Double> numberslist = new ArrayList<Double>(Arrays.asList(numbers));
        if (numberslist.stream().anyMatch(number -> number < min || number > max))
            throw new IllegalArgumentException(info);

    }
}
