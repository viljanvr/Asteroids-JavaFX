package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AsteroidTest {

    private Asteroid smallAsteroid;
    private Asteroid bigAsteroid;
    List<Sprite> sprites;

    @BeforeEach
    public void setup() {
        bigAsteroid = new Asteroid();
        smallAsteroid = (Asteroid) bigAsteroid.splitLargeAsteroid().get(0);
        smallAsteroid.setPosXY(300, 300);
        sprites = new ArrayList<>();
    }

    @Test
    @DisplayName("Test random asteroid constructor")
    public void testConstructor() {

        Boolean isAsteroidRandoAndNotOutOfBounds =
                // Test spawning on left side of the screen.
                (bigAsteroid.getPosX() == -64
                        && bigAsteroid.getPosY() >= 0
                        && bigAsteroid.getPosY() <= AsteroidsController.CANVASHEIGHT) ||
                // Cheks spawning on top side of the screen.
                        (bigAsteroid.getPosY() == -64
                                && bigAsteroid.getPosX() >= 0
                                && bigAsteroid.getPosX() <= AsteroidsController.CANVASWIDTH);
        assertTrue(isAsteroidRandoAndNotOutOfBounds);

    }

    @Test
    @DisplayName("Tests splitLargeAsteroid method")
    public void splitLargeAsteroidTest() {
        bigAsteroid.setPosXY(300, 400);
        sprites.addAll(bigAsteroid.splitLargeAsteroid());
        assertEquals(3, sprites.size(), "Checks that there are three total sprites");
        assertTrue(sprites.stream()
                .allMatch(sprite -> sprite instanceof Asteroid && !((Asteroid) sprite).isLarge()),
                "Checks that all sprites are of type small asteroid.");
        sprites.stream().forEach(sprite -> assertEquals(300, sprite.getPosX(),
                "Check that all new asteroids have correct x-coordinate"));
        sprites.stream().forEach(sprite -> assertEquals(400, sprite.getPosY(),
                "Check that all new asteroids have correct y-coordinate"));
        sprites.stream().forEach(sprite -> assertTrue(
                0.4 <= sprite.getVelocity().getLength() && sprite.getVelocity().getLength() <= 2.4,
                "Check that the velocity of the new asteroids is between 0.4 and 2.4 units, but was "
                        + sprite.getVelocity().getLength() + "."));
    }

    @Test
    @DisplayName("Tests the checkCollision method")
    public void checkCollisionTest() {
        Asteroid asteroid2 = (Asteroid) bigAsteroid.splitLargeAsteroid().get(0);
        asteroid2.setPosXY(305, 305);
        sprites.add(asteroid2);
        assertFalse(smallAsteroid.checkCollision(sprites),
                "Asteroid should never collide with another asteroid, eventhough they overlap.");

        sprites.add(new Laser(305, 305, 0, 0, true));
        assertTrue(smallAsteroid.checkCollision(sprites), "Checks that an asteroid collides with a laser");

        sprites.clear();
        sprites.add(new Spaceship());
        smallAsteroid.setPosXY(400, 300);
        assertTrue(smallAsteroid.checkCollision(sprites), "Checks that an asteroid collides with a spaceship");
    }

    @Test
    @DisplayName("Tests the isLarge function")
    public void isLargeTest() {
        assertFalse(smallAsteroid.isLarge(), "Test a small asteorid.");
        assertTrue(bigAsteroid.isLarge(), "Test a large asteroid.");
    }

}
