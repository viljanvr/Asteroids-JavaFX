package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Asteroid extends Sprite {

    private Asteroid(int x1, int x2, int imageWidth, int imageHeight, String imageURL) {
        super(x1, x2, imageWidth, imageHeight, imageURL);
    }

    public Asteroid(char normalAsteroid) {
        this(2, 2, 64, 64, "asteroids/asteroid.png");
        getVelocity().setLength(1);
        getVelocity().setAngle(Math.random() * 6);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> (sprite instanceof Laser || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.contains(sprite));
    }

}
