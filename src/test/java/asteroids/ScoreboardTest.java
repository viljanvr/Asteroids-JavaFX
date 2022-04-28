package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class ScoreboardTest {
    private ScoreBoard scoreBoard, scoreBoard2;
    private List<String> playerNames = new ArrayList<>(Arrays.asList("Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava",
            "Elijah", "Charlotte", "William", "Sophia", "James",
            "Amelia", "Benjamin", "Isabella", "Lucas", "Mia", "Henry", "Evelyn", "Alexander", "Harper"));
    private List<Integer> scores = new ArrayList<>(Arrays.asList(1010, 940, 1260, 120, 300, 1500, 970, 780, 990, 1410,
            940, 700, 810, 310, 780, 790, 1000, 1650, 290, 850));

    private final String FILE_NAME = "score_save";
    private final String PARENTDIRECTORY_NAME = "testsaves";

    @BeforeEach
    public void setup() {
        scoreBoard = new ScoreBoard(PARENTDIRECTORY_NAME, FILE_NAME);
        resetScoreFile();
    }

    @Test
    @DisplayName("Test making new parentfolder")
    public void makingParentFolderTest() {
        File directory = new File(PARENTDIRECTORY_NAME);
        deleteParentDirectory();
        assertFalse(directory.exists(), "Tests that parentfolder doesn't exist.");
        scoreBoard.load();
        assertTrue(directory.exists(), "Test that load() creates a parenfolder if it dosen't exist yet.");

        deleteParentDirectory();
        assertFalse(directory.exists(), "Tests that parentfolder doesn't exist.");
        scoreBoard.save();
        assertTrue(directory.exists(), "Test that save() creates a parenfolder if it dosen't exist yet.");
    }

    @Test
    @DisplayName("Test making new savefile")
    public void makingNewSavefileTest() {
        File file = new File(PARENTDIRECTORY_NAME + "/" + FILE_NAME + ".txt");
        deleteScoreFile();
        assertFalse(file.exists(), "Tests that scorefile doesn't exist.");
        scoreBoard.load();
        assertTrue(scoreBoard.getScores().isEmpty(),
                "Check that the there aren't any scores when the file doesn't exist.");

        assertFalse(file.exists(), "Tests that scorefile still doesn't exist.");
        scoreBoard.save();
        assertTrue(file.exists(), "Test that that save() will create a scorefile if it dosen't exist yet.");
    }

    @Test
    @DisplayName("Test adding scores")
    public void addScoreTest() {

        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.addScore("name", -1);
        }, "Test that score can't be negative.");

        ArrayList<Integer> addedScoresSorted = new ArrayList<>();

        assertEquals(new ArrayList<>(), scoreBoard.getScores(), "Check that the scorelist is empty.");

        for (int i = 0; i < 20; i++) {
            scoreBoard.addScore(playerNames.get(i), scores.get(i));

            // Find index where the score is supposed to be.
            addedScoresSorted.add(scores.get(i));
            Collections.sort(addedScoresSorted, Collections.reverseOrder());
            int correctIndex = addedScoresSorted.lastIndexOf(scores.get(i));

            assertEquals(i + 1, scoreBoard.getScores().size(),
                    "Check that the list contains correct amount of scores.");
            assertEquals(new Pair<>(playerNames.get(i), scores.get(i)), scoreBoard.getScores().get(correctIndex),
                    "Check that score and playername is added to index " + correctIndex + ".");
        }

    }

    @Test
    @DisplayName("Test highscore getHighScore method")
    public void getHighScoreTest() {
        assertEquals(0, scoreBoard.getHighScore(), "Check that the highscore is 0 when no scores are set");

        scoreBoard.addScore(playerNames.get(0), scores.get(0));
        assertEquals(scores.get(0), scoreBoard.getHighScore(), "Check that highscore is 0 when no scores are added.");

        scoreBoard.addScore(playerNames.get(1), scores.get(1));
        assertEquals(scores.get(0), scoreBoard.getHighScore(),
                "Add the first score, and check that highscore is updated.");

        scoreBoard.addScore(playerNames.get(2), scores.get(2));
        assertEquals(scores.get(2), scoreBoard.getHighScore(), "Add new score, but not a highscore.");

        scoreBoard.addScore(playerNames.get(3), scores.get(3));
        assertEquals(scores.get(2), scoreBoard.getHighScore(), "Add new highscore.");

    }

    @Test
    @DisplayName("Test saving and loading scores from file")
    public void loadAndSaveTest() {
        for (int i = 0; i < 20; i++) {
            scoreBoard.addScore(playerNames.get(i), scores.get(i));
        }
        scoreBoard2 = new ScoreBoard(PARENTDIRECTORY_NAME, FILE_NAME);
        assertEquals(scoreBoard.getScores(), scoreBoard2.getScores(),
                "Checks that the new scoreboard loads all entries in the file");
    }

    @Test
    @DisplayName("Test checkValidFileStrings method")
    public void checkValidFileStringTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ScoreBoard("parentdirectory", "");
        }, "Test that file name/parentdirectory name can't be empty.");

        assertThrows(IllegalArgumentException.class, () -> {
            new ScoreBoard("parentdirectory", "file name");
        }, "Test that file name/parentdirectory name can't include whitespaces.");

        assertThrows(IllegalArgumentException.class, () -> {
            new ScoreBoard("parentdirectory", "file#$!.");
        }, "Test that file name/parentdirectory name can't include special characters.");
    }

    private void resetScoreFile() {
        scoreBoard.getScores().clear();
        scoreBoard.save();
    }

    private void deleteScoreFile() {
        File file = new File(PARENTDIRECTORY_NAME + "/" + FILE_NAME + ".txt");
        file.delete();
    }

    private void deleteParentDirectory() {
        deleteScoreFile();
        File directory = new File(PARENTDIRECTORY_NAME);
        directory.delete();
    }
}
