package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Spaceship extends Sprite {

    private final String THRUSTIMAGE_URL = "asteroids/images/spaceship_thrust.png";
    private Boolean showThrust = false;
    private double rotation = 3 * Math.PI / 2;

    public Spaceship() {
        super(400, 300, 0, 0, 39, 23, "asteroids/images/spaceship.png");
        System.out.println(GameConfig.spaceship_acceleration_increase);
    }

    public void rotateLeft() {
        rotation -= GameConfig.spaceship_rotationSpeed;
    }

    public void rotateRight() {
        rotation += GameConfig.spaceship_rotationSpeed;
    }

    public double getRotation() {
        return rotation;
    }

    public Sprite shoot() {
        return new Laser(getPosX() + getImageWidth() / 2.0 - 4 + 15 * Math.cos(rotation),
                getPosY() + getImageHeight() / 2.0 - 4 + 15 * Math.sin(rotation),
                getVelocity().getLength(), getRotation(), true);
    }

    public void thrust() {
        showThrust = true;
        velocity.addXY(Math.cos(getRotation()) * GameConfig.spaceship_acceleration_increase,
                Math.sin(getRotation()) * GameConfig.spaceship_acceleration_increase);
    }

    private void aeroBrake() {
        velocity.addXY(GameConfig.spaceship_acceleration_reduction * velocity.getX(),
                GameConfig.spaceship_acceleration_reduction * velocity.getY());
    }

    @Override
    public String getImageURL() {
        if (showThrust) {
            showThrust = false;
            return THRUSTIMAGE_URL;
        }
        return super.getImageURL();
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
        aeroBrake();
    }

    public boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(
                sprite -> (sprite instanceof Laser && !((Laser) sprite).isFriendly()) || sprite instanceof UFO
                        || sprite instanceof Asteroid)
                .anyMatch(sprite -> overlapsSprite(sprite));
    }

}
