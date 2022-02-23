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
        // getVelocity().setLength(Math.pow(Math.E, -getVelocity().getLength()));
        // getVelocity().setAngle(getRotation());

        this.velocity.addXY(Math.cos(this.getRotation())*0.4, Math.sin(this.getRotation())*0.4);

    }

    public void updateVelocity(){
        this.velocity.addXY(-0.02*velocity.getX(), -0.02*velocity.getY());
    }

    public void updatePosition() {
        updateVelocity();
    
        this.x1 += velocity.getX();
        if (x1 > 800)
            x1 -= 864;
        x2 += velocity.getX();
        if (x2 > 800)
            x2 -= 800;
        y1 += velocity.getY();
        if (y1 > 600)
            y1 -= 664;
        y2 += velocity.getY();
        if (y2 > 600)
            y2 -= 600;
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
