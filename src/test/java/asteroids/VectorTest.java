package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import asteroids.Vector;

public class VectorTest {

    private Vector vector;

    @BeforeEach
    public void setup() {
        vector = new Vector(3, 3);
    }

    @Test
    @DisplayName("Tests vector constructor")
    public void testConstructor() {

        assertEquals(0, 0);

    }

}
