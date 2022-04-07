package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Spaceship extends Sprite {

    private String ThrustImageURL;
    private Boolean thrust = false;

    public Spaceship() {
        super(400, 300, 39, 23, "asteroids/spaceship.png");
        ThrustImageURL = "asteroids/spaceship-thrust.png";
    }

    public void rotateLeft() {
        rotation -= Math.PI / 45;
    }

    public void rotateRight() {
        rotation += Math.PI / 45;
    }

    public Sprite shoot() {
        Sprite laser = new Laser(getPosX() + getImageWidth() / 2, getPosY() + getImageHeight() / 2);
        laser.getVelocity().setLength(getVelocity().getLength() + 4);
        laser.getVelocity().setAngle(getRotation());
        return laser;
    }

    public void thrust() {
        thrust = true;
        velocity.addXY(Math.cos(this.getRotation()) * 0.2, Math.sin(this.getRotation()) * 0.2);
    }

    private void aeroBrake() {
        velocity.addXY(-0.02 * velocity.getX(), -0.02 * velocity.getY());
    }

    @Override
    public String getImageURL() {
        if (thrust){
            thrust = false;
            return ThrustImageURL;
        }
        return imageURL;
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
