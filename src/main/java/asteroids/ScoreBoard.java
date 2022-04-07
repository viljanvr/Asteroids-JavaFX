package asteroids;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class ScoreBoard implements SaveHandler{

    private List<Pair<String, Integer>> highScores = new ArrayList<>();
    private String fileName;


    public ScoreBoard(String fileName) {
        this.fileName = fileName;
        highScores = load();
    }

    public void addScore(String player, int score){
        highScores.add(new Pair<>(player, score));
        sort();
        save();
    }

    public List<Pair<String, Integer>> getHighScores(){
        return highScores;
    }

    @Override
    public void save(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for(Pair<String, Integer> entry : highScores){
                writer.write(entry.getKey() + ":"+ entry.getValue() +"\n");
            }
            writer.close();
        } catch (FileNotFoundException e){
            new File(SaveHandler.class.getResource("").getFile()+"saves").mkdir();
            save();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePath() {
        return SaveHandler.class.getResource("").getFile()+"saves/"+fileName+".txt";

    }

    @Override
    public List<Pair<String, Integer>> load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            List<Pair<String, Integer>> list = reader.lines().map(element -> element.split(":")).map(element -> new Pair<String, Integer>(element[0], Integer.parseInt(element[1]))).collect(Collectors.toList());
            reader.close();
            return list;
        } catch (FileNotFoundException e){
            new File(SaveHandler.class.getResource("").getFile()+"saves").mkdir();
            return new ArrayList<>();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sort(){
        Collections.sort(highScores, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
    }

    public ObservableList<String> getScores(){
        ObservableList<String> scores = FXCollections.observableArrayList();
        highScores.stream().forEach(element -> scores.add(element.getKey() + " : " + element.getValue()));
        return scores;
    }

    public String toString(){
        String string = "";
        for (Pair<String, Integer> element : highScores){
            string += element.getKey() + " : " + element.getValue() + "\n";
        }
        return string;
    }



    
}
