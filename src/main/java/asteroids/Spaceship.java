package asteroids;

import java.lang.Math;

public class Spaceship extends Sprite {

    public Spaceship(int x1, int y1) {
        super(x1, y1, 64, 64, "asteroids/spaceship.png");

    }

    public Spaceship(int x1, int y1, Vector velocity) {
        super(x1, y1, 64, 64, velocity, "asteroids/spaceship.png");
    }

    public void rotateLeft() {
        rotate(Math.PI / 6);
    }

    public void rotateRight() {
        rotate(-Math.PI / 6);
    }

    public Sprite shoot() {
        Sprite lazer = new Sprite(getPosX(), getPosY(), 64, 64, "asteroids/spaceship.png");
        lazer.getVelocity().setLength(2);
        lazer.getVelocity().setAngle(getRotation());
        return lazer;
    }

    public void thrust() {
        getVelocity().setLength(Math.pow(Math.E, -getVelocity().getLength()));
        getVelocity().setAngle(getRotation());

    }

    public static void main(String[] args) {
        System.out.println("hello");
        Spaceship spaceship = new Spaceship(0, 0);
        System.out.println(spaceship.getVelocity().toString());
        spaceship.thrust();
        System.out.println(spaceship.getVelocity().toString());
        spaceship.thrust();
        System.out.println(spaceship.getVelocity().toString());

    }

}
