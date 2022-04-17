package asteroids;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SaveHandler {

	public void save() throws FileNotFoundException, IOException;

	public void load() throws FileNotFoundException;

}
