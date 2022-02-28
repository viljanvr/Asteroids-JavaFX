package asteroids;

import java.lang.Math;

public class Asteroid extends Sprite {

    public Asteroid() {
        super((double) Math.random() * (700), (double) Math.random() * (500), 64, 64, "asteroids/asteroid.png");
        getVelocity().setLength(4);
        getVelocity().setAngle(Math.random() * 6);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }
}
