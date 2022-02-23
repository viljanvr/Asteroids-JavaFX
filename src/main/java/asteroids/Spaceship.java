package asteroids;

import java.lang.Math;

public class Spaceship extends Sprite {
    private Vector acceleration;

    public Spaceship(int x1, int y1) {
        super(x1, y1, "asteroids/spaceship.png");
        acceleration = new Vector(0, 0);

    }

    public Spaceship(int x1, int y1, Vector velocity) {
        super(x1, y1, velocity, "asteroids/spaceship.png");
        acceleration = new Vector(0, 0);

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
        acceleration.setLength(Math.pow(Math.E, -this.getVelocity().getLength())); // Bruker e^-x hvor x er farten for å
                                                                                   // angi en akselerasjon.
        acceleration.setAngle(this.getRotation());
        // this.getVelocity().addVector(new Vector(length, angle));

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
