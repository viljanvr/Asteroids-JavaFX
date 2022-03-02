package asteroids;

import java.util.Collection;

public class Lazer extends Sprite {
    public Lazer(double x1, double y1) {
        super(x1, y1, 8, 8, "asteroids/laser.png");
    }

    public boolean checkOutOfBound() {
        if (x1 > AsteroidsController.CanvasWidth || x2 < 0 || y1 > AsteroidsController.CanvasHeight || y2 < 0)
            return true;
        return false;
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> sprite instanceof Asteroid)
                .anyMatch(asteroid -> this.contains(asteroid));
    }

}
