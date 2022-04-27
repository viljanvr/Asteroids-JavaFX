package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Spaceship extends Sprite {

    private final String THRUSTIMAGEURL = "asteroids/spaceship-thrust.png";
    private Boolean showThrust = false;
    private double rotation = 3 * Math.PI / 2;

    public Spaceship() {
        super(400, 300, 0, 0, 39, 23, "asteroids/spaceship.png");
    }

    public void rotateLeft() {
        rotation -= Math.PI / 45;
    }

    public void rotateRight() {
        rotation += Math.PI / 45;
    }

    public double getRotation() {
        return rotation;
    }

    public Sprite shoot() {
        return new Laser(getPosX() + getImageWidth() / 2, getPosY() + getImageHeight() / 2, getVelocity().getLength(),
                getRotation());
    }

    public void thrust() {
        showThrust = true;
        velocity.addXY(Math.cos(getRotation()) * 0.2, Math.sin(getRotation()) * 0.2);
    }

    private void aeroBrake() {
        velocity.addXY(-0.02 * velocity.getX(), -0.02 * velocity.getY());
    }

    @Override
    public String getImageURL() {
        if (showThrust) {
            showThrust = false;
            return THRUSTIMAGEURL;
        }
        return super.getImageURL();
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
        aeroBrake();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> sprite instanceof Asteroid)
                .anyMatch(asteroid -> this.overlapsSprite(asteroid));
    }

}
