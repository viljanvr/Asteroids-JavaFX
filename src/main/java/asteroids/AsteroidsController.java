package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.scene.canvas.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AsteroidsController {
    public static final int CanvasWidth = 800, CanvasHeight = 600;
    public Timer timer;
    public Game game;

    private boolean UPpressed = false, DOWNpressed = false, DOWNreleased = true, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true;

    @FXML
    private AnchorPane background;

    @FXML
    public Pane gameArea;

    @FXML
    public Canvas canvas = new Canvas(CanvasWidth, CanvasHeight);
    public GraphicsContext gc;

    @FXML
    private ListView<String> scoreBoard;

    @FXML
    private Text currentScore;

    // initializes the game
    public void initialize() {
        timer = new Timer();
        game = new Game();


        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

        // starter AnimationTimer
        timer.start();

    }

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                UPpressed = true;
                break;
            case DOWN:
                DOWNpressed = true;
                break;
            case LEFT:
                LEFTpressed = true;
                break;
            case RIGHT:
                RIGHTpressed = true;
                break;
            case SPACE:
                SPACEpressed = true;
                break;
            default:
                break;
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                UPpressed = false;
                break;
            case DOWN:
                DOWNpressed = false;
                DOWNreleased = true;
                break;
            case LEFT:
                LEFTpressed = false;
                break;
            case RIGHT:
                RIGHTpressed = false;
                break;
            case SPACE:
                SPACEpressed = false;
                SPACEreleased = true;
                break;
            default:
                break;
        }
    }

    public void spaceshipAction(Spaceship spaceship) {
        if (this.UPpressed)
            spaceship.thrust();
        if (this.LEFTpressed)
            spaceship.rotateLeft();
        if (this.RIGHTpressed)
            spaceship.rotateRight();

        if (this.DOWNpressed && this.DOWNreleased) {
            game.getSprites().add(new Asteroid());
            this.DOWNreleased = false;
        }

        if (this.SPACEpressed && this.SPACEreleased) {
            game.getSprites().add(spaceship.shoot());
            this.SPACEreleased = false;
        }
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

    //BURDE ENDRES SLIK AT DET IKKE KJØRES LIKE OFTE
    private void updateCurrentScore() {
		currentScore.setText("Score: " + game.getScore());
    }

    // AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

            game.gameLoop();
            updateCurrentScore();
            
            // renders all the objects on screen
            game.getSprites().stream().forEach((sprite) -> {
                renderSprite(sprite);
            });

            // controls spaceship actions
            spaceshipAction(game.getSpaceship());

            // System.out.println(
            //         game.getSpaceship().velocity.toString() + "(" + game.getSpaceship().getPosX() + ", " + game.getSpaceship().getX2() + ")");

        }
    };
}
