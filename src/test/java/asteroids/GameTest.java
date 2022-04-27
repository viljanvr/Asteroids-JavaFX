package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;
    private Spaceship previousSpaceship;
    private Asteroid asteroid;

    @BeforeEach
    public void setup() {
        game = new Game();
        asteroid = (Asteroid) new Asteroid().splitLargeAsteroid().get(0);
        asteroid.setPosXY(500, 500);
    }

    @Test
    @DisplayName("Test constructor")
    public void constructorTest() {

        checkSpriteCount(1, 0, 1, 0);

        assertEquals(0, game.getScore(), "Check that score is 0.");
        assertEquals(3, game.getLives(), "Check that the remaing lives are 3.");
    }

    @Test
    @DisplayName("Test spaceship collision with large asteroid")
    public void testLargeAsteroidCollision() {
        game.getSpaceship().setPosXY(500, 500);
        previousSpaceship = game.getSpaceship();
        game.getSprites().get(1).setPosXY(500, 500);
        checkSpriteCount(1, 0, 1, 0);

        game.gameLoop(0);
        checkSpriteCount(1, 0, 0, 3);
        assertNotEquals(previousSpaceship, game.getSpaceship());

        assertEquals(20, game.getScore(), "Check that score is 20.");
        assertEquals(2, game.getLives(), "Check that remaing lives are 2");
        assertFalse(game.isGameOver(), "Check that the game still isn't over.");
    }

    @Test
    @DisplayName("Test spaceship collision with small asteroid")
    public void testSmallAsteroidCollision() {
        game.getSpaceship().setPosXY(500, 500);
        game.getSprites().add(asteroid);
        checkSpriteCount(1, 0, 1, 1);

        game.gameLoop(0);
        checkSpriteCount(1, 0, 1, 0);
        assertEquals(400, game.getSpaceship().getPosX(), "Check that the x-coordinate for the spaceship is 400.");
        assertEquals(300, game.getSpaceship().getPosY(), "Check that the y-coordinate for the spaceship is 300.");

        assertEquals(10, game.getScore(), "Check that score is 10.");
        assertEquals(2, game.getLives(), "Check that remaing lives are 2");
        assertFalse(game.isGameOver(), "Check that the game still isn't over.");
    }

    @Test
    @DisplayName("Test laser hitting large asteroid")
    public void testLargeAsteroidHit() {
        previousSpaceship = game.getSpaceship();
        game.getSprites().get(1).setPosXY(500, 500);
        game.getSprites().add(new Laser(500, 500, 0, 0));
        checkSpriteCount(1, 1, 1, 0);

        game.gameLoop(0);
        checkSpriteCount(1, 0, 0, 3);
        assertEquals(previousSpaceship, game.getSpaceship());

        assertEquals(20, game.getScore(), "Check that score is 20.");
        assertEquals(3, game.getLives(), "Check that remaing lives are 3");
        assertFalse(game.isGameOver(), "Check that the game still isn't over.");
    }

    @Test
    @DisplayName("Test laser hitting small asteroid")
    public void testSmallAsteroidHit() {
        game.getSprites().add(asteroid);
        game.getSprites().add(new Laser(500, 500, 0, 0));
        checkSpriteCount(1, 1, 1, 1);

        game.gameLoop(0);
        checkSpriteCount(1, 0, 1, 0);
        assertEquals(400, game.getSpaceship().getPosX(), "Check that the x-coordinate for the spaceship is 400.");
        assertEquals(300, game.getSpaceship().getPosY(), "Check that the y-coordinate for the spaceship is 300.");

        assertEquals(10, game.getScore(), "Check that score is 10.");
        assertEquals(3, game.getLives(), "Check that remaing lives are 3");
        assertFalse(game.isGameOver(), "Check that the game still isn't over.");
    }

    public void checkSpriteCount(int spaceships, int lasers, int largeAsteroids, int smallAsteroids) {
        assertEquals(spaceships, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Spaceship).count(),
                "Checks that there are " + spaceships + " spaceship(s).");

        assertEquals(lasers, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Laser).count(), "Checks that there are " + lasers + " laser(s).");

        assertEquals(largeAsteroids, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()).count(),
                "Checks that there are " + largeAsteroids + " large asteroid(s).");

        assertEquals(smallAsteroids, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && !((Asteroid) sprite).isLarge()).count(),
                "Checks that there are " + smallAsteroids + " small asteroid(s).");
    }

}
