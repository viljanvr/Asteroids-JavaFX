package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpriteTest {

        // Minimalistic implementation of sprite to test functionality.
        public class SpriteImplementation extends Sprite {

                public SpriteImplementation(double x1, double y1, double speed, double speedDirection, int imageWidth,
                                int imageHeight, String IMAGEURL) {
                        super(x1, y1, speed, speedDirection, imageWidth, imageHeight, IMAGEURL);
                }

                @Override
                public Boolean checkCollision(Collection<Sprite> list) {
                        return false;
                }

        }

        private SpriteImplementation sprite1, sprite2;
        private final double DELTA = 0.01;

        @BeforeEach
        public void setup() {
                sprite1 = new SpriteImplementation(300, 400, 5, Math.PI / 2, 20, 10, "asteroids/exampleURL.png");
                sprite2 = new SpriteImplementation(200, 200, 3, 7 * Math.PI / 6, 30, 30, "asteroids/exampleURL.png");
        }

        @Test
        @DisplayName("Test Sprite super constructor")
        public void ConstructorTest() {

                assertEquals(300, sprite1.getPosX(), "Test x-coordinate.");
                assertEquals(400, sprite1.getPosY(), "Test y-coordinate.");
                assertEquals(20, sprite1.getImageWidth(), "Test image width.");
                assertEquals(10, sprite1.getImageHeight(), "Test image height.");
                assertEquals("asteroids/exampleURL.png", sprite1.getImageURL(), "Test image URL.");
                assertEquals(5, sprite1.getVelocity().getLength(), "Test velocity.");
                assertEquals(0, sprite1.getVelocity().getX(), DELTA,
                                "Test that angle is correct, by  checking the x-component of velocity.");
                assertEquals(5, sprite1.getVelocity().getY(), DELTA,
                                "Test that angle is correct, by  checking the y-component of velocity.");

        }

        @Test
        @DisplayName("Test changing position for sprite")
        public void setPosXYTest() {
                sprite1.setPosXY(500, 200);
                assertEquals(500, sprite1.x1, "Checks upper left coordinate.");
                assertEquals(520, sprite1.x2, "Checks upper right coordinate.");
                assertEquals(200, sprite1.y1, "Checks lower left coordinate.");
                assertEquals(210, sprite1.y2, "Checks lower right coordinate.");
        }

        @Test
        @DisplayName("Test overlapsSprite function")
        public void overlapsSpriteTest() {
                sprite2.setPosXY(200, 400);

                sprite1.setPosXY(229, 400);
                assertTrue(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is overlapping the right side of sprite2.");

                sprite1.setPosXY(230, 400);
                assertFalse(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is to the right of sprite2, but not overlapping.");

                sprite1.setPosXY(200, 429);
                assertTrue(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is overlapping the bottom side of sprite2.");

                sprite1.setPosXY(200, 430);
                assertFalse(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is beneath sprite2, but not overlapping.");

                sprite1.setPosXY(181, 400);
                assertTrue(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is overlapping the left side of sprite2.");

                sprite1.setPosXY(180, 400);
                assertFalse(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is to the left of sprite2, but not overlapping.");

                sprite1.setPosXY(200, 391);
                assertTrue(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is overlapping the upper side of sprite2.");

                sprite1.setPosXY(200, 390);
                assertFalse(sprite1.overlapsSprite(sprite2),
                                "Checks when sprite1 is above sprite2, but not overlapping.");

        }

        @Test
        @DisplayName("Test isInsideRectangle function")
        public void isInsideRectangleTest() {
                sprite2.setPosXY(499, 300);
                assertTrue(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is overlapping the right side of rectangle defined by the two corner coordinates (300, 200) and (500, 400).");

                sprite2.setPosXY(500, 300);
                assertFalse(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is to the right of the rectangle, but not overlapping.");

                sprite2.setPosXY(400, 399);
                assertTrue(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is overlapping the bottom side of rectangle defined by the two corner coordinates (300, 200) and (500, 400).");

                sprite2.setPosXY(400, 400);
                assertFalse(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is beneath the rectangle, but not overlapping.");

                sprite2.setPosXY(271, 300);
                assertTrue(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is overlapping the left side of rectangle defined by the two corner coordinates (300, 200) and (500, 400).");

                sprite2.setPosXY(270, 300);
                assertFalse(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is to the left of the rectangle, but not overlapping.");

                sprite2.setPosXY(400, 171);
                assertTrue(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is overlapping the upper side of rectangle defined by the two corner coordinates (300, 200) and (500, 400).");

                sprite2.setPosXY(400, 170);
                assertFalse(sprite2.isInsideRectangle(300, 200, 500, 400),
                                "Checks when sprite2 is above the rectangle, but not overlapping.");

        }

        @Test
        @DisplayName("Test updatePosition function")
        public void updatePositionTest() {

                sprite1.updatePosition();
                sprite2.updatePosition();
                assertEquals(300, sprite1.getPosX(), DELTA,
                                "Checks that the sprite2's x-coordinate remains unchanged.");
                assertEquals(400 + 5, sprite1.getPosY(), DELTA,
                                "Checks that the sprite2's y-coordinate has increased with 5 units.");
                assertEquals(200 - (3 * Math.sqrt(3) / 2), sprite2.getPosX(), DELTA,
                                "Checks that the sprite2's x-coordinate has decreased with 3*sqrt(3)/2 units.");
                assertEquals(198.5, sprite2.getPosY(), DELTA,
                                "Checks that the sprite2's y-coordinate has decreased with 3/2 units.");
        }

        @Test
        @DisplayName("Test wrap functions")
        public void wrapTest() {
                sprite1.setPosXY(801, 400);
                sprite1.wrap();
                assertEquals(-19, sprite1.getPosX(),
                                "Check that x-coordinate is changed when the sprite has gone out of the image from the right.");
                assertEquals(400, sprite1.getPosY(),
                                "Check that y-coordinate remains unchanged when the sprite has gone out of the image from the right.");
                sprite1.wrap();
                assertEquals(-19, sprite1.getPosX(), "Check that wrap doesn't change x-coordinate.");
                assertEquals(400, sprite1.getPosY(), "Check that wrap doesn't change y-coordinate.");

                sprite1.setPosXY(300, 601);
                sprite1.wrap();
                assertEquals(300, sprite1.getPosX(),
                                "Check that x-coordinate remains unchanged when the sprite has gone out of the image from the bottom.");
                assertEquals(-9, sprite1.getPosY(),
                                "Check that y-coordinate is changed when the sprite has gone out of the image from the bottom.");
                sprite1.wrap();
                assertEquals(300, sprite1.getPosX(), "Check that wrap doesn't change x-coordinate.");
                assertEquals(-9, sprite1.getPosY(), "Check that wrap doesn't change y-coordinate.");

                sprite1.setPosXY(-21, 400);
                sprite1.wrap();
                assertEquals(799, sprite1.getPosX(),
                                "Check that x-coordinate is changed when the sprite has gone out of the image from the left.");
                assertEquals(400, sprite1.getPosY(),
                                "Check that y-coordinate remains unchanged when the sprite has gone out of the image from the left.");
                sprite1.wrap();
                assertEquals(799, sprite1.getPosX(), "Check that wrap doesn't change x-coordinate.");
                assertEquals(400, sprite1.getPosY(), "Check that wrap doesn't change y-coordinate.");

                sprite1.setPosXY(300, -11);
                sprite1.wrap();
                assertEquals(300, sprite1.getPosX(),
                                "Check that x-coordinate remains unchanged when the sprite has gone out of the image from the top.");
                assertEquals(599, sprite1.getPosY(),
                                "Check that y-coordinate is changed when the sprite has gone out of the image from the top.");
                sprite1.wrap();
                assertEquals(300, sprite1.getPosX(), "Check that wrap doesn't change x-coordinate.");
                assertEquals(599, sprite1.getPosY(), "Check that wrap doesn't change y-coordinate.");
        }

        @Test
        @DisplayName("Test exception throws")
        public void exceptionThrowTest() {
                // Tests for less that min.
                checkEceptionThrow(-65, 0, 0, 0);
                checkEceptionThrow(0, -65, 0, 0);
                checkEceptionThrow(0, 0, -1, 0);
                checkEceptionThrow(0, 0, 0, -1);
                // Tests for more than max.
                checkEceptionThrow(1000, 0, 0, 0);
                checkEceptionThrow(0, 1000, 0, 0);
                checkEceptionThrow(0, 0, 100, 0);
                checkEceptionThrow(0, 0, 0, 100);

        }

        private void checkEceptionThrow(double x1, double y1, int imageWidth, int imageHeight) {
                assertThrows(IllegalArgumentException.class, () -> {
                        new SpriteImplementation(x1, y1, 0, 0, imageWidth, imageHeight, "");
                });
        }

}
