package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LaserTest {

    private Laser laser1, laser2, laser3;
    private Asteroid asteroid;
    private Spaceship spaceship;
    private static final double DELTA = 0.01;

    @BeforeEach
    public void setup() {
        laser1 = new Laser(0, 100, 1, 0, true);
        laser2 = new Laser(0, 0, 1, Math.PI / 4, true);
        laser3 = new Laser(300, 300, 6, 7 * Math.PI / 6, true);
    }

    @Test
    @DisplayName("Test Laser constructor")
    public void constructorTest() {
        assertEquals(300, laser3.getPosX(), "Test that x-coordinate is 300.");
        assertEquals(300, laser3.getPosY(), "Test that y-coordinate is 300.");
        assertEquals(10, laser3.getVelocity().getLength(), "Test that the velocity is 10.");
        assertEquals(-10 * Math.sqrt(3) / 2, laser3.getVelocity().getX(), DELTA,
                "Checks that the angle of the velocity vector is 7*PI/6, by checking the x-component of the velocity.");
        assertEquals(-10 / 2, laser3.getVelocity().getY(), DELTA,
                "Checks that the angle of the velocity vector is 7*PI/6, by checking the y-component of the velocity.");
        assertEquals(8, laser3.getImageWidth(), "Checks that image width is 8.");
        assertEquals(8, laser3.getImageHeight(), "Checks that image height is 8.");
        assertEquals("asteroids/laser.png", laser3.getImageURL(), "Checks that image URL is correct.");
    }

    @Test
    @DisplayName("Test checkOutOfBounds function")
    public void outOfBoundsTest() {
        laserOutOfBoundsTest(laser1, 161);
        laserOutOfBoundsTest(laser2, 170);
        laserOutOfBoundsTest(laser3, 36);
    }

    private void laserOutOfBoundsTest(Laser laser, int framesToOutOfBounds) {
        assertFalse(laser.checkOutOfBound(), "Checks that the laser is inside the playable area.");
        for (int i = 0; i < framesToOutOfBounds - 1; i++) {
            laser.updatePosition();
        }
        assertFalse(laser.checkOutOfBound(), "Checks that the laser still is inside the playable area after "
                + (framesToOutOfBounds - 1) + " frames.");

        laser.updatePosition();
        assertTrue(laser.checkOutOfBound(),
                "Cheks that the laser is outside the playable area after " + framesToOutOfBounds + " frames.");
    }

    @Test
    @DisplayName("Tests collision function")
    public void checkCollisionTest() {
        // asteroid = (Asteroid) new Asteroid().splitLargeAsteroid().get(0);
        asteroid.setPosXY(0, 100);
        assertTrue(laser1.checkCollision(Arrays.asList(asteroid)), "Checks that laser collides with asteroid.");

        spaceship = new Spaceship();
        spaceship.setPosXY(0, 100);
        assertFalse(laser1.checkCollision(Arrays.asList(spaceship)),
                "Laser should never collide with spaceship, eventhough they overlap.");

        laser2.setPosXY(0, 100);
        assertFalse(laser1.checkCollision(Arrays.asList(laser2)),
                "Laser should never collide with another laser, eventhough they overlap.");
    }

}
