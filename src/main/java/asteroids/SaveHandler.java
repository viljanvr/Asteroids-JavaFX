package asteroids;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SaveHandler{

	public void save(String filename) throws FileNotFoundException, IOException;

	public Game load(String filename) throws FileNotFoundException;

}

