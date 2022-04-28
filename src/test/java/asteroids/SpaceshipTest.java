package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpaceshipTest {

        private Spaceship spaceship;

        @BeforeEach
        public void setup() {
                spaceship = new Spaceship();
                // super(400, 300, 0, 0, 39, 23, "asteroids/spaceship.png");
        }

        @Test
        @DisplayName("Test rotate functions")
        public void rotateFunctionsTest() {
                spaceship.rotateLeft();
                assertEquals(3 * Math.PI / 2 - Math.PI / 45, spaceship.getRotation(),
                                "Checks rotation after rotated left");
                spaceship.rotateRight();
                spaceship.rotateRight();
                assertEquals(3 * Math.PI / 2 + Math.PI / 45, spaceship.getRotation(),
                                "Checks rotation after rotated right");
        }

        @Test
        @DisplayName("Test shoot function")
        public void shootTest() {
                Sprite laser = spaceship.shoot();
                assertEquals(spaceship.getPosX() + spaceship.getImageWidth() / 2.0 - 4, laser.getPosX(),
                                "Checks laser x-position");
                assertEquals(spaceship.getPosY() + spaceship.getImageHeight() / 2.0 - 4 - 15, laser.getPosY(),
                                "Checks laser y-position");
                spaceship.rotateRight();
                spaceship.getVelocity().setLength(5);
                Sprite laser2 = spaceship.shoot();
                assertEquals(Math.cos(spaceship.getRotation()),
                                laser2.getVelocity().getX() / laser2.getVelocity().getLength(),
                                0.01);
                assertEquals(Math.sin(spaceship.getRotation()),
                                laser2.getVelocity().getY() / laser2.getVelocity().getLength(),
                                0.01);
                // "Checks if laser rotation is equivalent to spaceship rotation");
                assertEquals(spaceship.getVelocity().getLength() + 4, laser2.getVelocity().getLength(), 0.001,
                                "checks laser speed after chanding spaceship speed ");

        }

        @Test
        @DisplayName("Tests thrust function")
        public void thrustTest() {
                spaceship.thrust();
                assertEquals(Math.cos(3 * Math.PI / 2) * 0.2, spaceship.getVelocity().getX(),
                                "Checks speed increase in x-cordinate");
                assertEquals(Math.sin(3 * Math.PI / 2) * 0.2, spaceship.getVelocity().getY(),
                                "Checks speed increase in y-cordinate");
                spaceship.rotateRight();

                assertEquals(Math.cos(3 * Math.PI / 2 + Math.PI / 45) * 0.2,
                                spaceship.getVelocity().getX(), 0.01,
                                "Checks speed increase in x-cordinate after rotation");
                assertEquals(Math.sin(Math.PI / 45) * 0.2, spaceship.getVelocity().getY(),
                                0.1,
                                "Checks speed increase in y-cordinate after rotation");

        }

        @Test
        @DisplayName("Tests aeroBrake function")
        public void aeroBrakeTest() {
                // increases the speed to one
                for (int i = 0; i < 5; i++) {
                        spaceship.thrust();
                }
                double velocityX = spaceship.getVelocity().getX();
                spaceship.updatePosition();
                // TODO:
                // assertEquals(1 - 0.02 * velocityX, spaceship.getVelocity().getX(),
                // 0.1, "Checks velocity in x coordinate after speed is 1");
        }

        @Test
        @DisplayName("Tests getImageURL")
        public void getImageURLTest() {
                assertEquals("asteroids/spaceship.png", spaceship.getImageURL(), "Checks the url without thrust");
                spaceship.thrust();
                assertEquals("asteroids/spaceship-thrust.png", spaceship.getImageURL(),
                                "Checks the url when thrust is active");
                assertEquals("asteroids/spaceship.png", spaceship.getImageURL(),
                                "Checks the url when thrust is no longer active");

        }

        @Test
        @DisplayName("Tests the collision detector function")
        public void checkCollisionTest() {
                Collection<Sprite> sprites = new ArrayList<>();
                sprites.add(new Laser(405, 305, 0, 0));
                assertFalse(spaceship.checkCollision(sprites), "Checks collision with laser");
                Asteroid asteroid = (Asteroid) new Asteroid().splitLargeAsteroid().get(0);
                asteroid.setPosXY(405, 305);
                sprites.add(asteroid);
                assertTrue(spaceship.checkCollision(sprites), "Checks collision with asteroid");
        }

}
