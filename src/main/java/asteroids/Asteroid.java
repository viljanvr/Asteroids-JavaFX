package asteroids;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Asteroid extends Sprite {

    public Asteroid() {
        this(Math.random());
    }

    // Constructor for Large Asteroids.
    private Asteroid(double randomNumber) {
        super(randomNumber > 0.5 ? Math.random() * AsteroidsController.CANVASWIDTH : -54,
                randomNumber < 0.5 ? Math.random() * AsteroidsController.CANVASHEIGHT : -55,
                1, Math.random() * 6.28, 54, 55,
                "asteroids/asteroid.png");
    }

    // Constructor for Dwarf Asteroids
    private Asteroid(int x1, int y1, Vector velocity) {
        super(x1, y1, 1.4, Math.random() * 6.28, 37, 38, "asteroids/dwarf_asteroid.png");
        getVelocity().addXY(velocity.getX(), velocity.getY());
    }

    public List<Sprite> splitLargeAsteroid(long currentTime) {
        List<Sprite> list = new ArrayList<>(Arrays.asList(new Asteroid((int) getPosX(), (int) getPosY(), getVelocity()),
                new Asteroid((int) getPosX(), (int) getPosY(), getVelocity()),
                new Asteroid((int) getPosX(), (int) getPosY(), getVelocity())));
        list.addAll(Debris.creatDebris(false, getPosX(), getPosY(), currentTime));
        return list;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(
                sprite -> sprite instanceof Laser || sprite instanceof Spaceship)
                .anyMatch(sprite -> overlapsSprite(sprite));
    }

    public boolean isLarge() {
        return getImageWidth() == 54;
    }

    public List<Sprite> explode(long currentTime) {
        return Debris.creatDebris(false, getPosX(), getPosY(), currentTime);
    }
}
