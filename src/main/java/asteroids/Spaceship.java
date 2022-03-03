package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Spaceship extends Sprite {

    public Spaceship(double x1, double y1) {
        super(x1, y1, 64, 64, "asteroids/spaceship.png");
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
        Sprite laser = new Laser(getPosX() + getImageWidth() / 2, getPosY() + getImageHeight() / 2);
        laser.getVelocity().setLength(getVelocity().getLength() + 4);
        laser.getVelocity().setAngle(getRotation());
        return laser;
    }

    public void thrust() {
        velocity.addXY(Math.cos(this.getRotation()) * 0.2, Math.sin(this.getRotation()) * 0.2);
    }

    public void aeroBrake() {
        velocity.addXY(-0.02 * velocity.getX(), -0.02 * velocity.getY());
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
        aeroBrake();

    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> sprite instanceof Asteroid)
                .anyMatch(asteroid -> this.contains(asteroid));
    }

}
