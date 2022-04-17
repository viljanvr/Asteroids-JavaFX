package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpriteTest {

    private Spaceship spaceship;

    @BeforeEach
    public void setup() {
        spaceship = new Spaceship();
    }

    @Test
    @DisplayName("Test Sprite super constructor")
    public void testConstructor() {

        assertEquals(spaceship.getPosX(), 400);
        assertEquals(spaceship.getPosY(), 300);
        assertEquals(spaceship.getImageWidth(), 39);
        assertEquals(spaceship.getImageHeight(), 23);

    }

    @Test
    @DisplayName("Test collision functions")
    public void b() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Test updatePosition functions")
    public void c() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Test wrap functions")
    public void d() {
        assertEquals(0, 0);
    }

}
