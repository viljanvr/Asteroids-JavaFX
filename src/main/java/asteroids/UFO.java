package asteroids;

import java.util.Collection;

public class UFO extends Sprite {

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
                sprite -> ((sprite instanceof Laser && ((Laser) sprite).getFriendly()) || sprite instanceof Spaceship))
                .anyMatch(sprite -> overlapsSprite(sprite));
    }

    public Laser shootTowardSpaceship(Double x1, Double y1) {
        double result = Math.atan2((y1 - this.y1), (-this.x1 + x1));
        return new Laser(getPosX() + getImageWidth() / 2 - 4 + 25 * Math.cos(result),
                getPosY() + getImageHeight() / 2 - 4 + 15 * Math.sin(result), 1.5, result, false);
    }

}
