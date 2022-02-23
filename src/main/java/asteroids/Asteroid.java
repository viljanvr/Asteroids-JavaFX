package asteroids;

import java.lang.Math;

public class Asteroid extends Sprite {

    public Asteroid() {
        super((int) Math.random() * (9), (int) Math.random() * (9), "asteroids.asteroid.png");
        getVelocity().setAngle(Math.random() * 6);
    }

}
