package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LaserTest {

    private Laser laser1, laser2;
    private Asteroid asteroid1, asteroid2;
    private static final double DELTA = 0.01;

    @BeforeEach
    public void setup() {
        laser1 = new Laser(0, 100, 1,0);
        laser2 = new Laser(0, 0, 1, Math.PI/4);
    }

    @Test
    @DisplayName("Test checkOutOfBounds function")
    public void outOfBoundsTest() {
        assertFalse(laser1.checkOutOfBound() ,"Sjekke at laseren er inne på skjermen");
        for(int i = 0; i<161; i++){
            laser1.updatePosition();
        }
        assertEquals(805, laser1.getPosX(), DELTA, "Sjekke at laseren har riktig x-posisjon etter at 160 frames har kjørt");
        assertEquals(100, laser1.getPosY(), DELTA, "Sjekke at laseren har riktig y-posisjon etter at 160 frames har kjørt");
        assertTrue(laser1.checkOutOfBound() , "Sjekke at laseren er utenfor skjermen ettar at 160 frames har kjørt.");

        

        assertFalse(laser2.checkOutOfBound() ,"Sjekke at laseren er inne på skjermen");
        for(int i = 0; i<170; i++){
            laser2.updatePosition();
        }
        assertEquals(601.04, laser2.getPosX(), DELTA, "Sjekke at laseren har riktig x-posisjon etter at 170 frames har kjørt");
        assertEquals(601.04, laser2.getPosY(), DELTA, "Sjekke at laseren har riktig y-posisjon etter at 170 frames har kjørt");
        assertTrue(laser2.checkOutOfBound() , "Sjekke at laseren er utenfor skjermen ettar at 170 frames har kjørt.");


    }

    @Test
    @DisplayName("Tests collision function")
    public void b() {
        asteroid1 = new Asteroid(0, 100, new Vector(0, 0));
        assertTrue(laser1.checkCollision(Arrays.asList(asteroid1)), "Sjekk at laser1 kolliderer med asteroide1");
        assertFalse(laser2.checkCollision(Arrays.asList(asteroid1)), "Sjekk at laser2 ikke kolliderer med asteroide1");

        asteroid2 = new Asteroid(0, 0, new Vector(0, 0));
        assertFalse(laser1.checkCollision(Arrays.asList(asteroid2)), "Sjekk at laser1 ikke kolliderer med asteroide2");
        assertTrue(laser2.checkCollision(Arrays.asList(asteroid2)), "Sjekk at laser2 kolliderer med asteroide2");
    }

}
