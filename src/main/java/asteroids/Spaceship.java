package asteroids;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Spaceship extends Sprite {

    private final String THRUSTIMAGE_URL = "asteroids/spaceship_thrust.png";
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
        return new Laser(getPosX() + getImageWidth() / 2.0 - 4 + 15 * Math.cos(rotation),
                getPosY() + getImageHeight() / 2.0 - 4 + 15 * Math.sin(rotation),
                getVelocity().getLength(), getRotation(), true);
    }

    public void thrust() {
        showThrust = true;
        velocity.addXY(Math.cos(getRotation()) * 0.18, Math.sin(getRotation()) * 0.18);
    }

    private void aeroBrake() {
        velocity.addXY(-0.02 * velocity.getX(), -0.02 * velocity.getY());
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
                sprite -> (sprite instanceof Laser && !((Laser) sprite).getFriendly()) || sprite instanceof UFO
                        || sprite instanceof Asteroid)
                .anyMatch(sprite -> overlapsSprite(sprite));
    }

    public List<Sprite> dead(long currentTime) {
        return Arrays.asList(new Debris(getPosX(), getPosY(), 33, 3, "asteroids/debris_long.png", currentTime),
                new Debris(getPosX(), getPosY(), 33, 3, "asteroids/debris_long.png", currentTime),
                new Debris(getPosX(), getPosY(), 33, 3, "asteroids/debris_long.png", currentTime));
    }

}
