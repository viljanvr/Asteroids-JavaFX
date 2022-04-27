package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
    }

    @Test
    @DisplayName("Test constructor")
    public void constructorTest() {
        
        checkSpriteCount(1, 0, 1, 0);

        assertEquals(0, game.getScore(), "Check that score is 0.");
        assertEquals(3, game.getLives(), "Check that the remaing lives are 3.");
    }

    @Test
    @DisplayName("Test that correct sprites are added/removed when spaceship collides with large asteroid")
    public void testLargeAsteroidCollision(){
        Spaceship previousSpaceship = game.getSpaceship();
        collideSpaceshipLargeAsteroid();
        checkSpriteCount(1, 0, 0, 3);
        assertNotEquals(previousSpaceship, game.getSpaceship(), "Check that a new spaceship has been spawned.");
    }

    @Test
    @DisplayName("Test that correct sprites are added/removed when spaceship collides with small asteroid")
    public void testSmallAsteroidCollision(){
        Spaceship previousSpaceship = game.getSpaceship();
        collideSpaceshipSmallAsteroid();
        checkSpriteCount(1, 0, 0, 0);
        assertNotEquals(previousSpaceship, game.getSpaceship(), "Check that a new spaceship has been spawned.");
    }

    @Test
    @DisplayName("Test that correct sprites are added/removed when laser hits large asteroid")
    public void testLargeAsteroidHit(){
        Spaceship previousSpaceship = game.getSpaceship();
        collideLaserLargeAsteroid();
        checkSpriteCount(1, 0, 0, 3);
        assertEquals(previousSpaceship, game.getSpaceship(), "Check that the spaceship remians the same as before");
    }

    @Test
    @DisplayName("Test that correct sprites are added/removed when laser hits small asteroid")
    public void testSmallAsteroidHit(){
        Spaceship previousSpaceship = game.getSpaceship();
        collideLaserSmallAsteroid();
        checkSpriteCount(1, 0, 0, 0);
        assertEquals(previousSpaceship, game.getSpaceship(), "Check that the spaceship remians the same as before");
    }

    @Test
    @DisplayName("Test increasing score when an asteroid collides with another sprite.")
    public void scoreTest(){
        collideSpaceshipLargeAsteroid();
        assertEquals(20, game.getScore(), "Test that score is increased with 20 when spaceship collides with large asteroid.");
        
        collideSpaceshipSmallAsteroid();
        assertEquals(30, game.getScore(), "Test that score is increased with 10 when spaceship collides with small asteroid.");

        collideLaserLargeAsteroid();
        assertEquals(50, game.getScore(), "Test that score is increased with 20 when laser hits large asteroid.");

        collideLaserSmallAsteroid();
        assertEquals(60, game.getScore(), "Test that score is increased with 10 when laser hits small asteroid.");
    }

    @Test
    @DisplayName("Test losing lives and isGameOver")
    public void deadTest(){
        assertEquals(3,game.getLives(), "Check that the player has three lives left");
        assertFalse(game.isGameOver(), "Check that game is not over.");

        collideSpaceshipLargeAsteroid();
        assertEquals(2,game.getLives(), "Check that player looses a life after colliding with a large Asteroid");

        collideSpaceshipSmallAsteroid();
        assertEquals(1,game.getLives(), "Check that player looses a life after colliding with a small Asteroid");
        
        collideLaserLargeAsteroid();
        assertEquals(1,game.getLives(), "Check that laser colliding with large asteroid doesn't affect lives left");

        collideLaserSmallAsteroid();
        assertEquals(1,game.getLives(), "Check that laser colliding with small asteroid doesn't affect lives left");

        collideSpaceshipLargeAsteroid();
        assertEquals(0,game.getLives(), "Check that the player has zero lives left");
        assertFalse(game.isGameOver(), "Check that game is not over when having zero lives left.");

        collideSpaceshipSmallAsteroid();
        assertTrue(game.isGameOver(), "Check that game is over.");
    }

    @Test
    @DisplayName("Prevent spawning spaceship when object is the way.")
    public void spawnKillPrevetionTest(){
        game.getSprites().removeIf(sprite -> sprite instanceof Spaceship);
        game.getSprites().add(new Asteroid(400, 300, new Vector(0, 0)));
        game.gameLoop(0);
        assertEquals(0, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Spaceship).count() , "Checks that spaceship doesn't spawn when an asteroid is in the way.");

        game.getSprites().removeIf(sprite -> sprite instanceof Asteroid);
        game.gameLoop(0);
        assertEquals(1, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Spaceship).count() , "Checks that s spaceship spawns when the asteroids have dissepeared.");
    }

    @Test
    @DisplayName("Test that a large asteroid is spawned every 5 seconds")
    public void testAsteroidSpawning(){
        game.gameLoop(800000000000000l);
        assertEquals(2, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()).count() , "Check that game spawns a second asteroid first time gameLoop runs.");
        game.gameLoop(800005000000000l);
        assertEquals(3, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()).count() , "Check that an asteroid has spawned after 5 seconds.");
        game.gameLoop(800010000000000l);
        assertEquals(4, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()).count() , "Checks that another asteroid has spawned after 5 more seconds.");
    }



    public void checkSpriteCount(int spaceships, int lasers, int largeAsteroids, int smallAsteroids){
        assertEquals(spaceships, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Spaceship).count() , "Checks that there are " + spaceships + " spaceship(s).");

        assertEquals(lasers, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Laser).count() , "Checks that there are " + lasers + " laser(s).");

        assertEquals(largeAsteroids, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && ((Asteroid)sprite).isLarge()).count() ,"Checks that there are " + largeAsteroids + " large asteroid(s).");

        assertEquals(smallAsteroids, game.getSprites().stream()
                .filter(sprite -> sprite instanceof Asteroid && !((Asteroid)sprite).isLarge()).count() , "Checks that there are " + smallAsteroids+ " small asteroid(s).");
    }

    public void collideSpaceshipLargeAsteroid(){
        game.getSprites().removeIf(sprite -> !(sprite instanceof Spaceship));
        game.getSpaceship().setPosXY(500, 500);
        game.getSprites().add(new Asteroid());
        game.getSprites().get(1).setPosXY(500, 500);
        game.gameLoop(0);
    }

    public void collideSpaceshipSmallAsteroid(){
        game.getSprites().removeIf(sprite -> !(sprite instanceof Spaceship));
        game.getSpaceship().setPosXY(500, 500);
        game.getSprites().add(new Asteroid(500, 500, new Vector(0, 0)));
        game.gameLoop(0);
    }

    public void collideLaserLargeAsteroid(){
        game.getSprites().removeIf(sprite -> !(sprite instanceof Spaceship));
        game.getSprites().add(new Asteroid());
        game.getSprites().get(1).setPosXY(500, 500);
        game.getSprites().add(new Laser(500, 500, 0, 0));
        game.gameLoop(0);
    }

    public void collideLaserSmallAsteroid(){
        game.getSprites().removeIf(sprite -> !(sprite instanceof Spaceship));
        game.getSprites().add(new Asteroid(500, 500, new Vector(0, 0)));
        game.getSprites().add(new Laser(500, 500, 0, 0));
        game.gameLoop(0);
    }

}
