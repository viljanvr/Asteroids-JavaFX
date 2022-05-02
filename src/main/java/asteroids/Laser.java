package asteroids;

import java.util.Collection;

public class Laser extends Sprite {

    private boolean friendly;

    public Laser(double x1, double y1, double speed, double speedDirection, boolean friendly) {
        super(x1, y1, speed + 4, speedDirection, 8, 8, (friendly ? "asteroids/laser.png" : "asteroids/laser_red.png"));
        this.friendly = friendly;
    }

    public boolean checkOutOfBound() {
        return x1 > AsteroidsController.CANVASWIDTH || x2 < 0 || y1 > AsteroidsController.CANVASHEIGHT || y2 < 0;
    }

    public boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> sprite instanceof Asteroid)
                .anyMatch(asteroid -> this.overlapsSprite(asteroid));
    }

    public boolean getFriendly() {
        return friendly;
    }

}
