package asteroids;

import java.lang.Math;

import javafx.stage.Window;

public class Spaceship extends Sprite {

    public Spaceship(double x1, double y1) {
        super(x1, y1, 64, 64, "asteroids/spaceship.png");

    }

    public Spaceship(double x1, double y1, Vector velocity) {

        super(x1, y1, 64, 64, velocity, "asteroids/spaceship.png");
    }

    public void rotateLeft() {
        rotate(-Math.PI / 45);
    }

    public void rotateRight() {
        rotate(Math.PI / 45);
    }

    public void rotate(double angle) {
        rotation += angle;
    }

    public Sprite shoot() {
        Sprite lazer = new Sprite(getPosX() + getImageWidth() / 2, getPosY() + getImageHeight() / 2, 8, 8,
                "asteroids/laser.png");
        lazer.getVelocity().setLength(4);
        lazer.getVelocity().setAngle(getRotation());
        return lazer;
    }

    public void thrust() {
        this.velocity.addXY(Math.cos(this.getRotation()) * 0.2, Math.sin(this.getRotation()) * 0.2);
    }

    public void aeroBrake() {
        this.velocity.addXY(-0.02 * velocity.getX(), -0.02 * velocity.getY());
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        aeroBrake();

    }

    public static void main(String[] args) {
        System.out.println("hello");
        Spaceship spaceship = new Spaceship(0, 0);
        System.out.println(spaceship.getRotation());
        spaceship.rotate(Math.PI / 4);
        System.out.println(spaceship.getRotation());
    }

}
