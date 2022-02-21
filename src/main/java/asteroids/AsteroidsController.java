package asteroids;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AsteroidsController {
    
    

    @FXML public Canvas board = new Canvas(800,600);

    public GraphicsContext gc = board.getGraphicsContext2D();
    
    public void initialize(){
        gc.save();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 800, 600);
    }
    
    
}
