package asteroids;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;

public class AsteroidsController {
    public static final int CanvasWidth = 800, CanvasHeight = 600;
    public Timer timer;
    public Game game;

    private boolean UPpressed = false, DOWNpressed = false, DOWNreleased = true, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true;

    @FXML
    public Canvas canvas = new Canvas(CanvasWidth, CanvasHeight);
    public GraphicsContext gc;

    @FXML
    private Text currentScore;

    @FXML
    private Text livesLeft;

    @FXML
    private Text gameStatus;

    Media sound;
    MediaPlayer mediaPlayer;

    // initializes the game
    public void initialize() {

        try {
            sound = new Media(getClass().getClassLoader().getResource("asteroids/boom.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer(sound);

        timer = new Timer();
        game = new Game();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

        // starter AnimationTimer
        timer.start();

        gameStatus.setText("");
        gameStatus.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && game.isGameOver()) {
                gameStatus.setText("");
                game = new Game();
            }
        });

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
            game.getSprites().add(new Asteroid(true));
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
        gc.drawImage(new Image(sprite.getImageURL()), 0, 0);
        gc.restore();
    }

    // BURDE ENDRES SLIK AT DET IKKE KJØRES LIKE OFTE
    private void updateCurrentScore() {
        currentScore.setText("Score: " + game.getScore());
    }

    private void updateLivesLeft() {
        livesLeft.setText(game.getLives() + " lives left");
    }

    private void showGameStatus() {
        if (game.isGameOver())
            gameStatus.setText("new Game");
    }

    private void soundEffectHandle() {
        if (game.soundEffectHandle()) {
            mediaPlayer.play();
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    // AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

            soundEffectHandle();
            game.gameLoop(nanotime);
            updateCurrentScore();
            updateLivesLeft();
            showGameStatus();

            // renders all the objects on screen
            game.getSprites().stream().forEach((sprite) -> {
                renderSprite(sprite);
            });

            // controls spaceship actions
            spaceshipAction(game.getSpaceship());

        }
    };
}
