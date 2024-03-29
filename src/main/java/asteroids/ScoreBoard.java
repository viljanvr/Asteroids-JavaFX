package asteroids;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class ScoreBoard implements SaveHandler {

    private List<Pair<String, Integer>> scoresList = new ArrayList<>();
    private final String FILE_NAME;
    private final String PARENTDIRECTORY_NAME;

    public ScoreBoard(String PARENTDIRECTORY_NAME, String FILE_NAME) {
        checkValidFileString(PARENTDIRECTORY_NAME, "Directory name can only include letters, numbers and underscores.");
        checkValidFileString(FILE_NAME, "File name can only include letters, numbers and underscores.");

        this.PARENTDIRECTORY_NAME = PARENTDIRECTORY_NAME;
        this.FILE_NAME = FILE_NAME;
        load();
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (Pair<String, Integer> entry : scoresList) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            new File(PARENTDIRECTORY_NAME).mkdir();
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            scoresList = reader.lines().map(element -> element.split(":"))
                    .map(element -> new Pair<String, Integer>(element[0], Integer.parseInt(element[1])))
                    .collect(Collectors.toList());
            reader.close();
        } catch (FileNotFoundException e) {
            new File(PARENTDIRECTORY_NAME).mkdir();
            scoresList = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String player, int score) {
        if (player.isBlank() || player.isEmpty() || score < 0) {
            throw new IllegalArgumentException("innvalid score");
        }
        scoresList.add(new Pair<>(player, score));
        scoresList.sort((score1, score2) -> score2.getValue() - score1.getValue());
        save();
    }

    public int getHighScore() {
        return scoresList.isEmpty() ? 0 : scoresList.get(0).getValue();
    }

    public List<Pair<String, Integer>> getScores() {
        return scoresList;

    }

    private String getFilePath() {
        return PARENTDIRECTORY_NAME + "/" + FILE_NAME + ".txt";

    }

    private void checkValidFileString(String s, String message) {
        if (!Pattern.matches("[a-zA-Z0-9_]+", s))
            throw new IllegalArgumentException(message);
    }
}
