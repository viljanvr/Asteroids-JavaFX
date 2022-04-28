package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
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

        Boolean isAsteroidRandom;
        // Checks that asteroid spawns on the left side of the screen.
        if (bigAsteroid.getPosX() == -64)
            isAsteroidRandom = bigAsteroid.getPosY() >= 0
                    || bigAsteroid.getPosY() <= AsteroidsController.CANVASHEIGHT;
        // or that asteroids spawns on the top side of the screen.
        else if (bigAsteroid.getPosX() >= 0 || bigAsteroid.getPosX() <= AsteroidsController.CANVASWIDTH)
            isAsteroidRandom = bigAsteroid.getPosY() == -64;
        else
            isAsteroidRandom = false;
        assertTrue(isAsteroidRandom);
    }

    @Test
    @DisplayName("Tests splitLargeAsteroid function")
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
    @DisplayName("Tests the collision detector function")
    public void checkCollisionTest() {
        Asteroid asteroid2 = (Asteroid) bigAsteroid.splitLargeAsteroid().get(0);
        asteroid2.setPosXY(305, 305);
        sprites.add(asteroid2);
        assertFalse(smallAsteroid.checkCollision(sprites),
                "Checks that an asteroid doesn't collide with another asteroid");
        sprites.add(new Laser(305, 305, 0, 0));
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
