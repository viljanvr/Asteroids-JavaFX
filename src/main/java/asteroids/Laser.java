package asteroids;

import java.util.Collection;

public class Laser extends Sprite {
    public Laser(double x1, double y1, double speed, double speedDirection) {
        super(x1, y1, speed + 4, speedDirection, 8, 8, "asteroids/laser.png");
    }

    public boolean checkOutOfBound() {
        return x1 > AsteroidsController.CANVASWIDTH || x2 < 0 || y1 > AsteroidsController.CANVASHEIGHT || y2 < 0;
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> sprite instanceof Asteroid)
                .anyMatch(asteroid -> this.overlapsSprite(asteroid));
    }

}
