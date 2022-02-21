package asteroids;

import java.lang.Math;

public class Spaceship extends Sprite {

    public Spaceship(int x1, int y1) {
        super(x1, y1, "asteroids.spaceship.txt");

    }

    public void rotateLeft() {
        this.getVelocity().rotate(Math.PI / 6);
    }

    public void rotateRight() {
        this.getVelocity().rotate(-Math.PI / 6);
    }

    public Sprite shoot() {
        Sprite lazer = new Sprite(getPosX(), getPosY(), "lazer image path");
        lazer.getVelocity().setAngle(getVelocity().getAngle());
        return lazer;
    }

    public void thrust() {
        updatePosition();
    }

}
