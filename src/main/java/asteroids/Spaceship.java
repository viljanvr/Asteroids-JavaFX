package asteroids;

import java.lang.Math;

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
        // rotation = Math.acos(Math.cos(this.getRotation() + angle));
        this.rotation += angle;
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

        this.velocity.addXY(Math.cos(this.getRotation())*0.6, Math.sin(this.getRotation())*0.6);

    }

    public void updateVelocity(){
        this.velocity.addXY(-0.02*velocity.getX(), -0.02*velocity.getY());

    }

    public void updatePosition() {
        updateVelocity();
    
        x1 += velocity.getX();
        if (x1 > 800) {
            x1 -= 864;
            x2 -= 864;
        }
        x2 += velocity.getX();
        if (x2 < 0){
            x1 += 864;
            x2 += 864;
        }
        y1 += velocity.getY();
        if (y1 > 600){
            y1 -= 664;
            y2 -= 664;
        }
        y2 += velocity.getY();
        if (y2 < 0){
            y1 += 664;
            y2 += 664;
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");
        Spaceship spaceship = new Spaceship(0, 0);
        System.out.println(spaceship.getRotation());
        spaceship.rotate(Math.PI/4);
        System.out.println(spaceship.getRotation());
    }

}
