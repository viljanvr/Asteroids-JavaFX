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

    private Asteroid asteroid;
    private Asteroid randomAsteroid;
    List<Sprite> sprites;

    @BeforeEach
    public void setup() {
        randomAsteroid = new Asteroid();
        asteroid = (Asteroid) randomAsteroid.splitLargeAsteroid().get(0);
        asteroid.setPosXY(300, 300);
        sprites = new ArrayList<>();
    }

    @Test
    @DisplayName("Test random asteroid constructor and also tha it is not out of bounds")
    public void testConstructor() {

        Boolean isAsteroidRandoAndNotOutOfBounds =
                // Test spawning on left side of the screen.
                (randomAsteroid.getPosX() == -64
                        && randomAsteroid.getPosY() >= 0
                        && randomAsteroid.getPosY() <= AsteroidsController.CANVASHEIGHT) ||
                // Cheks spawning on top side of the screen.
                        (randomAsteroid.getPosY() == -64
                                && randomAsteroid.getPosX() >= 0
                                && randomAsteroid.getPosX() <= AsteroidsController.CANVASWIDTH);
        assertTrue(isAsteroidRandoAndNotOutOfBounds);
    }

    @Test
    @DisplayName("Tests splitLargeAsteroid function")
    public void splitLargeAsteroidTest() {
        sprites.addAll(asteroid.splitLargeAsteroid());
        assertEquals(3, sprites.size(), "Checks if the astroids was split into three dwarf asteriods");
        assertTrue(sprites.stream()
                .allMatch(sprite -> sprite instanceof Asteroid && sprite.getPosX() == 300 && sprite.getPosY() == 300),
                "Checks if the attributes of dwarf asteroids matcht with the parent asteroids");
    }

    @Test
    @DisplayName("Tests the collision detector function")
    public void checkCollisionTest() {
        Collection<Sprite> sprites = new ArrayList<>();
        Asteroid asteroid2 = (Asteroid) randomAsteroid.splitLargeAsteroid().get(0);
        asteroid2.setPosXY(305, 305);
        sprites.add(asteroid2);
        assertFalse(asteroid.checkCollision(sprites), "Checks collision with another asteroid");
        sprites.add(new Laser(305, 305, 0, 0));
        assertTrue(asteroid.checkCollision(sprites), "Checks collision with a laser");
        sprites.clear();
        sprites.add(new Spaceship());
        asteroid.setPosXY(400, 300);
        assertTrue(asteroid.checkCollision(sprites), "Checks collision with a spaceship");
    }

    @Test
    @DisplayName("Tests the isLarge function")
    public void isLargeTest() {
        assertFalse(asteroid.isLarge());
        assertTrue(randomAsteroid.isLarge());
    }

}
