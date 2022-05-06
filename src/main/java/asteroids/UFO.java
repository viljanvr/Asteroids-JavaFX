package asteroids;

import java.util.Collection;
import java.util.List;

public class UFO extends Sprite {

    private final double SHOOT_SPREAD_ANGLE = Math.PI / 12;

    public UFO() {
        this(Math.random());
    }

    // Constructor for Large Asteroids.
    private UFO(double randomNumber) {
        super(randomNumber > 0.5 ? Math.random() * AsteroidsController.CANVASWIDTH : -50,
                randomNumber < 0.5 ? Math.random() * AsteroidsController.CANVASHEIGHT : -31,
                1,
                (randomNumber < 0.25 ? Math.random() * Math.PI / 2 - Math.PI / 4
                        : randomNumber < 0.5 ? Math.random() * Math.PI / 2 + 3 * Math.PI / 4
                                : randomNumber < 0.75 ? Math.random() * Math.PI / 2 + Math.PI / 4
                                        : Math.random() * Math.PI / 2 + 5 * Math.PI / 4),
                50, 31,
                "asteroids/UFO.png");
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(
                sprite -> ((sprite instanceof Laser && ((Laser) sprite).isFriendly()) || sprite instanceof Spaceship))
                .anyMatch(sprite -> overlapsSprite(sprite));
    }

    public Laser shootTowardSpaceship(Double x1, Double y1) {
        double result = Math.atan2((y1 - this.y1), (-this.x1 + x1));

        // Add a random spread of 30 degrees in either direciton
        result += Math.random() * 2 * SHOOT_SPREAD_ANGLE - SHOOT_SPREAD_ANGLE;
        return new Laser(getPosX() + getImageWidth() / 2 - 4 + 25 * Math.cos(result),
                getPosY() + getImageHeight() / 2 - 4 + 15 * Math.sin(result), 1.5, result, false);
    }

    public void changeDirection() {
        getVelocity().setAngle(Math.random() * 6.28);
    }

    public List<Sprite> explode(long currentTime) {
        return Debris.creatDebris(true, getPosX(), getPosY(), currentTime);
    }

}
