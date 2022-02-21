package asteroids;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class AsteroidsController {
    
    

    @FXML public Canvas canvas = new Canvas(800,600);

    public GraphicsContext gc;
    
    public void initialize(){
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 800, 600);
        gc.drawImage(new Image("asteroids/spaceship.png"), 100.0, 100.0);
    }
    
    
}
