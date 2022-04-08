package asteroids;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;

public class Asteroid extends Sprite {

    public static double randomNumber = Math.random();

    // Constructor for Large Asteroids
    public Asteroid() {
        super(randomNumber > 0.5 ? Math.random() * AsteroidsController.CANVASHEIGHT : -64,
                randomNumber < 0.5 ? Math.random() * AsteroidsController.CANVASHEIGHT : -64,
                1, Math.random() * 6.28, 54, 55,
                "asteroids/asteroid.png");

    }

    // Constructor for Dwarf Asteroids
    public Asteroid(int x1, int y1) {
        super(x1, y1, 1.4, Math.random() * 6.28, 37, 38, "asteroids/dwarf_asteroid.png");
    }

    public Collection<Sprite> splitLargeAsteroid() {
        Collection<Sprite> list = new ArrayList<>();
        list.add(new Asteroid((int) getPosX(), (int) getPosY()));
        list.add(new Asteroid((int) getPosX(), (int) getPosY()));
        list.add(new Asteroid((int) getPosX(), (int) getPosY()));
        return list;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> (sprite instanceof Laser || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.containsSprite(sprite));
    }

    public Boolean isLarge() {
        return getVelocity().getLength() == 1;
    }
}
