package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpaceshipTest {
    private Spaceship spaceship;
    private static final double DELTA = 0.01;

    @BeforeEach
    public void setup() {
        spaceship = new Spaceship();
    }

    @Test
    @DisplayName("Test rotate functions")
    public void rotateFunctionsTest() {
        spaceship.rotateLeft();
        assertEquals(3 * Math.PI / 2 - Math.PI / 45, spaceship.getRotation(),
                "Checks rotation after rotated left.");
        spaceship.rotateRight();
        spaceship.rotateRight();
        assertEquals(3 * Math.PI / 2 + Math.PI / 45, spaceship.getRotation(),
                "Checks rotation after rotated right.");
    }

    @Test
    @DisplayName("Test shoot function")
    public void shootTest() {
        Sprite laser = spaceship.shoot();
        assertEquals(spaceship.getPosX() + spaceship.getImageWidth() / 2.0 - 4, laser.getPosX(),
                "Checks laser x-position.");
        assertEquals(spaceship.getPosY() + spaceship.getImageHeight() / 2.0 - 4 - 15, laser.getPosY(),
                "Checks laser y-position.");
        spaceship.rotateRight();
        spaceship.getVelocity().setLength(5);
        Sprite laser2 = spaceship.shoot();
        assertEquals(Math.cos(spaceship.getRotation()),
                laser2.getVelocity().getX() / laser2.getVelocity().getLength(),
                DELTA,
                "Checks if laser's speed direction is equivalent to spaceship rotation, by checking the x-component of laser's velocity.");
        assertEquals(Math.sin(spaceship.getRotation()),
                laser2.getVelocity().getY() / laser2.getVelocity().getLength(),
                DELTA,
                "Checks if laser's speed directions is equivalent to spaceship rotation, by checking the y-component of laser's velocity.");
        assertEquals(spaceship.getVelocity().getLength() + 4, laser2.getVelocity().getLength(), DELTA,
                "checks laser speed after changing spaceship speed to 5.");

    }

    @Test
    @DisplayName("Tests thrust function")
    public void thrustTest() {
        spaceship.thrust();
        assertEquals(0, spaceship.getVelocity().getX(), DELTA,
                "Check that x-component of velocity remains unchanged when thrusting upwards.");
        assertEquals(-0.2, spaceship.getVelocity().getY(), DELTA,
                "Check that y-component of velocity decreases when thrusting upwards.");

        // Rotate 92 degrees to the right.
        for (int i = 0; i < 23; i++) {
            spaceship.rotateRight();
        }

        spaceship.thrust();
        assertEquals(0.2, spaceship.getVelocity().getX(), DELTA,
                "Check that x-component of velocity increases when thrusting to the right.");
        assertEquals(-0.2, spaceship.getVelocity().getY(), DELTA,
                "Check that y-component of velocity remains unchanged when thrusting to the right.");

        // Rotate 44 degrees to the right
        for (int i = 0; i < 11; i++) {
            spaceship.rotateRight();
        }

        spaceship.thrust();
        assertEquals(0.2 + 0.2 / Math.sqrt(2), spaceship.getVelocity().getX(), DELTA,
                "Check that x-component of velocity increases when thrusting to down right.");
        assertEquals(-0.2 + 0.2 / Math.sqrt(2), spaceship.getVelocity().getY(), DELTA,
                "Check that y-component of velocity increases when thrusting to down right.");
    }

    @Test
    @DisplayName("Tests aeroBrake function")
    public void aeroBrakeTest() {
        // increases the speed to one
        for (int i = 0; i < 5; i++) {
            spaceship.thrust();
        }

        spaceship.updatePosition();
        assertEquals(0, spaceship.getVelocity().getX(), DELTA,
                "Check that x-component of velocity remains unchanged when velocity direction is upwards.");
        assertEquals(-1 + 0.02, spaceship.getVelocity().getY(), DELTA,
                "Check that y-component of velocity slows down when velocity direction is upwards.");

        // Rotate 44 degrees to the left.
        for (int i = 0; i < 11; i++) {
            spaceship.rotateLeft();
        }
        for (int i = 0; i < 5; i++) {
            spaceship.thrust();
        }
        spaceship.updatePosition();
        assertEquals(Math.cos(226 * Math.PI / 180) - 0.02 * Math.cos(226 * Math.PI / 180),
                spaceship.getVelocity().getX(), DELTA,
                "Check that x-component of velocity slows down when velocity direction is left upwards.");
        ;
        assertEquals(-0.98 + Math.cos(226 * Math.PI / 180) + 0.02 * (0.98 + Math.cos(226 * Math.PI / 180)),
                spaceship.getVelocity().getY(), DELTA,
                "Check that y-component of velocity slows down when velocity direction is left upwards.");
    }

    @Test
    @DisplayName("Tests getImageURL")
    public void getImageURLTest() {
        assertEquals("asteroids/spaceship.png", spaceship.getImageURL(),
                "Checks the URL when player is not thrusting.");
        spaceship.thrust();
        assertEquals("asteroids/spaceship-thrust.png", spaceship.getImageURL(),
                "Checks the URl when player is thrusting.");
        assertEquals("asteroids/spaceship.png", spaceship.getImageURL(),
                "Checks the URL when player no longer is thrusting.");

    }

    @Test
    @DisplayName("Tests the collision detector function")
    public void checkCollisionTest() {
        Laser laser = new Laser(405, 305, 0, 0);
        assertFalse(spaceship.checkCollision(Arrays.asList(laser)),
                "Spaceship should never collide with laser, eventhough they overlap.");

        Asteroid asteroid = (Asteroid) new Asteroid().splitLargeAsteroid().get(0);
        asteroid.setPosXY(405, 305);
        assertTrue(spaceship.checkCollision(Arrays.asList(asteroid)),
                "Checks that spaceship collides with asteroid.");
    }
}
