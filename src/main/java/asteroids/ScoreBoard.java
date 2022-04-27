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
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class ScoreBoard implements SaveHandler {

    private List<Pair<String, Integer>> highScores = new ArrayList<>();
    private final String FILENAME = "score_saves";

    public ScoreBoard() {
        load();
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (Pair<String, Integer> entry : highScores) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            new File("saves").mkdir();
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            highScores = reader.lines().map(element -> element.split(":"))
                    .map(element -> new Pair<String, Integer>(element[0], Integer.parseInt(element[1])))
                    .collect(Collectors.toList());
            reader.close();
        } catch (FileNotFoundException e) {
            new File("saves").mkdir();
            highScores = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String player, int score) {
        highScores.add(new Pair<>(player, score));
        highScores.sort((score1, score2) -> score2.getValue() - score1.getValue());
        save();
    }

    public int getHighScore() {
        return highScores.isEmpty() ? 0 : highScores.get(0).getValue();
    }

    public ObservableList<String> getScores() {
        return highScores.stream().limit(18)
                .map(element -> highScores.indexOf(element) + 1 + ". " + element.getKey() + ": " + element.getValue())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private String getFilePath() {
        return "saves/" + FILENAME + ".txt";
    }
}
