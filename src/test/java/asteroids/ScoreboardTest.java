package asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.util.Pair;

public class ScoreboardTest {
    private ScoreBoard scoreBoard, scoreBoard2;
    private ArrayList <String> playerNames = new ArrayList<>(Arrays.asList("Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava", "Elijah", "Charlotte", "William", "Sophia", "James",
    "Amelia", "Benjamin", "Isabella", "Lucas", "Mia",	"Henry", "Evelyn", "Alexander", "Harper"));
    private ArrayList <Integer> scores = new ArrayList<>(Arrays.asList(1010, 940, 1260, 120, 300, 1500, 970, 780, 990, 1410, 940, 700, 810, 310, 780, 790, 1000, 1650, 290, 850));
    private ArrayList <Integer> addedScoresSorted = new ArrayList<>();
    

    @BeforeEach
    public void setup() {
        deleteScoresFile();
        scoreBoard = new ScoreBoard();
    }

    @Test
    @DisplayName("Test adding scores")
    public void addScoreTest(){

        assertEquals(new ArrayList<>(), scoreBoard.getScores(), "Check that the scorelist is empty.");
        
        for(int i = 0; i<20; i++){
            scoreBoard.addScore(playerNames.get(i), scores.get(i));
            
            //Find index where the score is supposed to be.
            addedScoresSorted.add(scores.get(i));
            Collections.sort(addedScoresSorted, Collections.reverseOrder());
            int correctIndex = addedScoresSorted.lastIndexOf(scores.get(i));

            assertEquals(i+1, scoreBoard.getScores().size(),
                 "Check that the list contains correct amount of scores.");
            assertEquals(new Pair<>(playerNames.get(i), scores.get(i)), scoreBoard.getScores().get(correctIndex),
                 "Check that score and playername is added to index " + correctIndex + ".");
        }
        
    }


    @Test
    @DisplayName("Test highscore is correct")
    public void getHighScoreTest() {
        assertEquals(0, scoreBoard.getHighScore(), "Check that the highscore is 0 when no scores are set");

        scoreBoard.addScore(playerNames.get(0), scores.get(0));
        assertEquals(scores.get(0), scoreBoard.getHighScore(), "Check that highscore is 0 when no scores are added.");

        scoreBoard.addScore(playerNames.get(1), scores.get(1));
        assertEquals(scores.get(0), scoreBoard.getHighScore(), "Add a score, and check that highscore is updated.");

        scoreBoard.addScore(playerNames.get(2), scores.get(2));
        assertEquals(scores.get(2), scoreBoard.getHighScore(), "Add new score that is worse than the previous, so highscore remains unchanged.");

        scoreBoard.addScore(playerNames.get(3), scores.get(3));
        assertEquals(scores.get(2), scoreBoard.getHighScore(), "Add new highscore.");


    }

    @Test
    @DisplayName("Test saving and loading scores from file")
    public void loadAndSaveTest(){
        for(int i = 0; i<20; i++){
            scoreBoard.addScore(playerNames.get(i), scores.get(i));
        }

        scoreBoard2 = new ScoreBoard();
        assertEquals(scoreBoard.getScores(), scoreBoard2.getScores(), "Checks that the new scoreboard loads all entries in the file");
    }
        

    private void deleteScoresFile(){
        String filePath = ScoreboardTest.class.getResource("").getFile() + "saves/score_saves.txt";
        File f= new File(filePath);
        f.delete(); 
        
    }  
}
