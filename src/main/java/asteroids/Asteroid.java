package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Asteroid extends Sprite {

    public Asteroid() {
        super((double) Math.random() * (AsteroidsController.CanvasWidth),
                (double) Math.random() * (AsteroidsController.CanvasHeight), 64, 64, "asteroids/asteroid.png");
        getVelocity().setLength(1);
        getVelocity().setAngle(Math.random() * 6);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> (sprite instanceof Lazer || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.contains(sprite));
    }

}
