package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VectorTest {

    private Vector vector;

    @BeforeEach
    public void setup() {
        vector = new Vector(3, 3);
    }

    @Test
    @DisplayName("Test vector constructor")
    public void testConstructor() {
        checksXYPositions(3, 3);
    }

    @Test
    @DisplayName("Test addXY function")
    public void addXYTest() {
        vector.addXY(4, 4);
        checksXYPositions(7, 7);
    }

    @Test
    @DisplayName("Tests setAngle function")
    public void setAngleTest() {
        vector.setAngle(Math.PI);
        // TODO: what do here??
    }

    @Test
    @DisplayName("Tests getLength function")
    public void getLengthTest() {
        assertEquals(Math.sqrt(3 * 3 + 3 * 3), vector.getLength());
    }

    @Test
    @DisplayName("Tests setLength function")
    public void setLengthTest() {
        vector.setLength(8);
        assertEquals(8, vector.getLength());
    }

    @Test
    @DisplayName("Tests getters")
    public void gettersTest() {
        checksXYPositions(3, 3);
    }

    private void checksXYPositions(double x, double y) {
        assertEquals(x, vector.getX());
        assertEquals(y, vector.getY());
    }
}
