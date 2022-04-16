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
        highScores = load();
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (Pair<String, Integer> entry : highScores) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            new File(SaveHandler.class.getResource("").getFile() + "saves").mkdir();
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pair<String, Integer>> load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            List<Pair<String, Integer>> list = reader.lines().map(element -> element.split(":"))
                    .map(element -> new Pair<String, Integer>(element[0], Integer.parseInt(element[1])))
                    .collect(Collectors.toList());
            reader.close();
            return list;
        } catch (FileNotFoundException e) {
            new File(SaveHandler.class.getResource("").getFile() + "saves").mkdir();
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addScore(String player, int score) {
        highScores.add(new Pair<>(player, score));
        highScores.sort((score1, score2) -> score2.getValue() - score1.getValue());
        save();
    }

    public int getHighScore(int index) {
        return (highScores.isEmpty() ? 0 : highScores.get(index).getValue());
    }

    public ObservableList<String> getScores() {
        return highScores.stream().limit(18).map(element -> highScores.indexOf(element)+1 + ". " + element.getKey() + ": " + element.getValue())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private String getFilePath() {
        return SaveHandler.class.getResource("").getFile() + "saves/" + FILENAME + ".txt";
    }
}
