package asteroids;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;


public class AsteroidsController {
    public Timer timer;
    public Spaceship spaceship;
    Image spaceshipImage;
    
    

    @FXML public Canvas canvas = new Canvas(800,600);
    public GraphicsContext gc;

    // @FXML 
    // private ListView<String> scoreBoard;


    
    public void initialize(){
        timer = new Timer();

        spaceship = new Spaceship(0, 0, new Vector(1, 1));
        spaceshipImage = new Image(spaceship.getImageURL());
        spaceship.setImageSize((int) spaceshipImage.getWidth(), (int) spaceshipImage.getHeight());
        spaceship.setRotation(Math.PI/4);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 800, 600);

        // gc.drawImage(new Image("asteroids/64x64.png"), 100.0, 100.0);
        // gc.translate(100, 100);
        // gc.drawImage(new Image("asteroids/64x64.png"), 0.0, 0.0);
        // gc.translate(32, 32);
        // gc.drawImage(new Image("asteroids/64x64.png"), 0.0, 0.0);
        // gc.rotate(45);
        // gc.drawImage(new Image("asteroids/64x64.png"), 0.0, 0.0);
        // gc.translate(-32, -32);
        // gc.drawImage(new Image("asteroids/64x64.png"), 0.0, 0.0);
    
        //starter AnimationTimer
        timer.start();


        
    }

    public void renderSprite(Sprite sprite){
        gc.save();
        
        //Setter bildet på riktig koordinat. 
        gc.translate(sprite.getPosX(), sprite.getPosY());

        //Roterer bilde riktig vei
        gc.translate(sprite.getImageWidth()/2, sprite.getImageHeight()/2);
        gc.rotate(Math.toDegrees(sprite.getRotation()));
        gc.translate(-sprite.getImageWidth()/2, -sprite.getImageHeight()/2);

        //Tegner bildet.
        gc.drawImage(this.spaceshipImage, 0, 0);
        gc.restore();
    }


    //AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer{
        private double x = 1.0;
        @Override
        public void handle(long nanotime){
            renderSprite(spaceship);
            spaceship.updatePosition();
        }
        
    };  
}


