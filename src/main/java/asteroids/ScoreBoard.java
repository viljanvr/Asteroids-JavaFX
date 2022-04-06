package asteroids;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.util.Pair;

public class ScoreBoard implements SaveHandler{

    private List<Pair<String, Integer>> highScores = new ArrayList<>();

    // public ScoreBoard() {
    //     highScores = 
    // }

    public void addScore(String player, int score){
        highScores.add(new Pair<>(player, score));
        sort();
        save("score_saves");
    }

    public List<Pair<String, Integer>> getHighScores(){
        return highScores;
    }

    @Override
    public void save(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(filename)))) {
            for(Pair<String, Integer> entry : highScores){
                writer.write(entry.getKey() + ":"+ entry.getValue() +"\n");
                // System.out.println(entry.getKey() + ":"+ entry.getValue() +"\n");
            }
            writer.close();  
        } catch (IOException e) {
            return;
        }
    }

    private String getFilePath(String filename) {
        return SaveHandler.class.getResource("saves/").getFile()+filename+".txt";

    }

    @Override
    public List<String> load(String filename) throws FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    

    public void sort(){
        Collections.sort(highScores, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
    }



    public static void main(String[] args){
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.addScore("Viljan", 10000);
        scoreBoard.addScore("Arash", 6000);
        scoreBoard.addScore("Jakob", 21000);
        scoreBoard.addScore("Viljan", 500);
        scoreBoard.addScore("Jennie", 265);
        scoreBoard.addScore("Didrik", 8999);
        scoreBoard.addScore("Iver", 12500);
        
    }


    
}
