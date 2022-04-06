package asteroids;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.util.Pair;

public interface SaveHandler{

	public void save() throws FileNotFoundException, IOException;

	public List<Pair<String, Integer>> load() throws FileNotFoundException;

}

