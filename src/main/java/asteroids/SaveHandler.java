package asteroids;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SaveHandler{

	public void save(String filename) throws FileNotFoundException, IOException;

	public List<String> load(String filename) throws FileNotFoundException;

}

