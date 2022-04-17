package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AsteroidTest {

    private Asteroid asteroid;

    @BeforeEach
    public void setup() {
        asteroid = new Asteroid();
    }

    @Test
    @DisplayName("Test the two constructors")
    public void testConstructor() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Test collision function")
    public void b() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Tests splitLargeAsteroid function")
    public void c() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Possibly test isLarge")
    public void d() {
        assertEquals(0, 0);
    }

}
