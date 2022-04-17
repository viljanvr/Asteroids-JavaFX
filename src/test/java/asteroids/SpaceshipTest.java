package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @DisplayName("Test mathematical functions")
    public void testConstructor() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Test shoot function")
    public void b() {

    }

    @Test
    @DisplayName("Tests collision function")
    public void c() {

    }

}
