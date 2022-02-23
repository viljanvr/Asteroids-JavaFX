package asteroids;

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class AsteroidsController {
    public Timer timer;
    public Spaceship spaceship;
    public Spaceship s2;
    public Sprite lazer;
    Image spaceshipImage;
    Collection<Sprite> sprites = new ArrayList<>();

    private boolean UPpressed = false;
    private boolean DOWNpressed = false;

    @FXML
    private AnchorPane background;

    @FXML 
    public Pane gameArea;

    @FXML 
    public Canvas canvas = new Canvas(800, 600);

    public GraphicsContext gc;

    // @FXML
    // private ListView<String> scoreBoard;

    public void initialize() {
        timer = new Timer();

        spaceship = new Spaceship(0, 0, new Vector(1, 1));
        s2 = new Spaceship(200, 200);
        spaceship.rotate(Math.PI / 4);
        s2.rotate(Math.PI / 6);
        lazer = s2.shoot();

        sprites.add(spaceship);
        sprites.add(s2);
        sprites.add(lazer);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);


        // starter AnimationTimer
        timer.start();

    }

    @FXML
    public void keyPressed(KeyEvent event){
        System.out.println(event.getCode());
        switch (event.getCode()) {
            case SPACE:
                System.out.println("up");   
                break;
            case KP_LEFT:
                System.out.println("to the left");   
                break;
            default:
                break; 
        }
    }

    @FXML
    public void keyReleased(KeyEvent event){
        System.out.println("Released"+event.getCode());
    }

    public void renderSprite(Sprite sprite) {
        gc.save();

        // Setter bildet på riktig koordinat.
        gc.translate(sprite.getPosX(), sprite.getPosY());

        // Roterer bilde riktig vei
        gc.translate(sprite.getImageWidth() / 2, sprite.getImageHeight() / 2);
        gc.rotate(Math.toDegrees(sprite.getRotation()));
        gc.translate(-sprite.getImageWidth() / 2, -sprite.getImageHeight() / 2);

        // Tegner bildet.
        // gc.drawImage(this.spaceshipImage, 0, 0);
        gc.drawImage(new Image(sprite.getImageURL()), 0, 0);
        gc.restore();
    }

    // AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 800, 600);
            sprites.stream().forEach((sprite) -> {
                sprite.updatePosition();
                renderSprite(sprite);
            });
        }

    };
}
