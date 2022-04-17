package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreboardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    public void setup() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    @DisplayName("Test EVERYTHING")
    public void a() {
        assertEquals(0, 0);
    }

}
