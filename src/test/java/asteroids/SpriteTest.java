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
    private Laser laser1, laser2;
    private static final double DELTA = 0.01;

    @BeforeEach
    public void setup() {
        spaceship = new Spaceship();
    }

    @Test
    @DisplayName("Test Sprite super constructor")
    public void testConstructor() {

        assertEquals(400, spaceship.getPosX());
        assertEquals(300, spaceship.getPosY());
        assertEquals(39, spaceship.getImageWidth());
        assertEquals(23, spaceship.getImageHeight());
        assertEquals("asteroids/spaceship.png", spaceship.getImageURL());
        assertEquals(0, spaceship.getVelocity().getLength());
        assertEquals(0, spaceship.getRotation());

    }

    @Test
    @DisplayName("Test changing position for sprite")
    public void setPosXYTest() {
        spaceship.setPosXY(500, 200);
        assertEquals(500, spaceship.x1, "Checks upper left coordinate");
        assertEquals(539, spaceship.x2, "Checks upper right coordinate");
        assertEquals(200, spaceship.y1, "Checks lower left coordinate");
        assertEquals(223, spaceship.y2, "Checks lower right coordinate");


    }

    @Test
    @DisplayName("Test containsSprite")
    public void containsSpriteTest() {
        laser1 = new Laser(400, 300, 0, 0);
        assertTrue(spaceship.containsSprite(laser1), "Checks a laser that is in the middle of the spaceship.");

        laser1.setPosXY(438, 300);
        assertTrue(spaceship.containsSprite(laser1), "Checks a laser that is overlapping the right side of the spaceship");

        laser1.setPosXY(439, 300);
        assertFalse(spaceship.containsSprite(laser1), "Checks a laser that is to the right of the spaceship, but not overlapping");

        laser1.setPosXY(400, 322);
        assertTrue(spaceship.containsSprite(laser1), "Checks a laser that is overlapping the bottom side of the spaceship");

        laser1.setPosXY(400, 323);
        assertFalse(spaceship.containsSprite(laser1), "Checks a laser that is beneath the spaceship, but not overlapping");

        laser1.setPosXY(393, 300);
        assertTrue(spaceship.containsSprite(laser1), "Checks a laser that is overlapping the left side of the spaceship");

        laser1.setPosXY(392, 300);
        assertFalse(spaceship.containsSprite(laser1), "Checks a laser that is to the left of the spaceship, but not overlapping");

        laser1.setPosXY(400, 293);
        assertTrue(spaceship.containsSprite(laser1), "Checks a laser that is overlapping the upper side of the spaceship");

        laser1.setPosXY(400, 292);
        assertFalse(spaceship.containsSprite(laser1), "Checks a laser that is above the spaceship, but not overlapping");

    }

    @Test
    @DisplayName("Test updatePosition functions")
    public void c() {
        laser1 = new Laser(400, 300, 1, 0);
        laser2 = new Laser(400, 300, 1, Math.PI*3/4);

        for(int i = 1; i<5; i++){
            laser1.updatePosition();
            laser2.updatePosition();
            assertEquals(400+(i*5),laser1.getPosX(), DELTA, "Checks that the laser1's x-coordinate increases with 5 units every time");
            assertEquals(300,laser1.getPosY(), DELTA, "Checks that the laser1's y-coordinate remains unchanged");
            assertEquals(400-(5*i/Math.sqrt(2)),laser2.getPosX(), DELTA, "Checks that the laser2's x-coordinate decreases with 5/sqrt(2) units every time");
            assertEquals(300+(5*i/Math.sqrt(2)),laser2.getPosY(), DELTA, "Checks that the laser2's y-coordinate increases with 5/sqrt(2) units every time");
        }
    }

    @Test
    @DisplayName("Test wrap functions")
    public void wrapTest() {
        spaceship.setPosXY(801, 400);
        spaceship.wrap();
        assertEquals(-38, spaceship.getPosX(), "Check that x-coordinate is changed when the spaceship has gone out of the image from the right");
        assertEquals(400, spaceship.getPosY(), "Check that y-coordinate remains unchanged when the spaceship has gone out of the image from the right");
        spaceship.wrap();
        assertEquals(-38, spaceship.getPosX(), "Check that wrap doesn't change coordinate");
        assertEquals(400, spaceship.getPosY(), "Check that wrap doesn't change coordinate");

        spaceship.setPosXY(300, 601);
        spaceship.wrap();
        assertEquals(300, spaceship.getPosX(), "Check that x-coordinate remains unchanged when the spaceship has gone out of the image from the bottom");
        assertEquals(-22, spaceship.getPosY(), "Check that y-coordinate is changed  when the spaceship has gone out of the image from the bottom");
        spaceship.wrap();
        assertEquals(300, spaceship.getPosX(), "Check that wrap doesn't change coordinate");
        assertEquals(-22, spaceship.getPosY(), "Check that wrap doesn't change coordinate");
    }

}
