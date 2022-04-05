package asteroids;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashMap;

public class ScoreBoard implements SaveHandler{

    private HashMap<String, Integer> highScores = new HashMap<>();

    public void addScore(String player, int score){
        highScores.put(player, score);
    }

    public HashMap<String, Integer> getHighScores(){
        return highScores;
    }

    @Override
    public void save(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(filename)))) {
            for(String entry : getSortedList()){
                writer.write(entry+"\n");
                System.out.println(entry); 
            }
            writer.close();  
        } catch (IOException e) {
            throw e;
        }
    }

    private String getFilePath(String filename) {
        return SaveHandler.class.getResource("saves/").getFile()+filename+".txt";

    }

    @Override
    public Game load(String filename) throws FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    

    public List<String> getSortedList(){
        List<Entry<String, Integer>> list = new ArrayList<>(highScores.entrySet());
        list.sort(Entry.comparingByValue(Comparator.reverseOrder()));
        
        List<String> sortedScores = new ArrayList<>();
        
        for (Entry<String, Integer> score: list){
            sortedScores.add(score.getKey() + ": " + score.getValue());
        }
        return sortedScores;
    }



    public static void main(String[] args){
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.addScore("Viljan", 10000);
        scoreBoard.addScore("Arash", 6000);
        scoreBoard.addScore("Jakob", 21000);
        try {
            scoreBoard.save("save");
        } catch (IOException e) {
            System.out.println("Fil ikke funnet");;
        }
        
    }


    
}
