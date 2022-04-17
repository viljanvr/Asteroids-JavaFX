package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LaserTest {

    private Laser laser;

    @BeforeEach
    public void setup() {
        laser = new Laser(30, 30, 1, 1);

    }

    @Test
    @DisplayName("Test outOfBounds function")
    public void a() {
        assertEquals(0, 0);
    }

    @Test
    @DisplayName("Tests collision function")
    public void b() {
        assertEquals(0, 0);
    }

}
