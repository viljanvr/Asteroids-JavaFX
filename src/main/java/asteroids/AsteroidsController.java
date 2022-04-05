package asteroids;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.canvas.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class AsteroidsController {

    public static final int CanvasWidth = 800, CanvasHeight = 600;
    public Timer timer;
    public Game game;
    private boolean UPpressed = false, DOWNpressed = false, DOWNreleased = true, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true;
    Media sound;
    MediaPlayer mediaPlayer;

    @FXML
    public Canvas canvas = new Canvas(CanvasWidth, CanvasHeight);
    public GraphicsContext gc;

    @FXML
    private Text currentScore;

    @FXML
    private Text livesLeft;

    @FXML
    private Text gameStatus;

    @FXML
    private ListView<String> scoreBoard;

    

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

        // start rendering
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

        // starts AnimationTimer
        timer.start();
        
        updateScoreBoard();
        gameStatus.setText("");

        // keylistener to start new game if game is over
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
            case UP -> UPpressed = true;
            case DOWN ->DOWNpressed = true;
            case LEFT -> LEFTpressed = true;
            case RIGHT -> RIGHTpressed = true;
            case SPACE -> SPACEpressed = true;
            default -> {}
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> UPpressed = false;
            case DOWN -> {DOWNpressed = false; DOWNreleased = true;}
            case LEFT -> LEFTpressed = false;
            case RIGHT -> RIGHTpressed = false;
            case SPACE -> {SPACEpressed = false; SPACEreleased = true;}
            default -> {}
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

        // Places the image on the correct coordinate
        gc.translate(sprite.getPosX(), sprite.getPosY());

        // Rotates the image
        gc.translate(sprite.getImageWidth() / 2, sprite.getImageHeight() / 2);
        gc.rotate(Math.toDegrees(sprite.getRotation()));
        gc.translate(-sprite.getImageWidth() / 2, -sprite.getImageHeight() / 2);

        // Draws the image
        gc.drawImage(new Image(sprite.getImageURL()), 0, 0);
        gc.restore();
    }

    
    private void updateCurrentScore() {
        currentScore.setText("Score: " + game.getScore());
    }

    private void updateLivesLeft() {
        livesLeft.setText(game.getLives() + " lives left");
    }

    private void updateScoreBoard(){
        HashMap <String, Integer> highScores = new HashMap<>();
        highScores.put("Viljan", 1000);
        highScores.put("Jakob", 500);
        ObservableList<String> scores = FXCollections.observableArrayList();
        for (String player: highScores.keySet()){
            scores.add(player + ": " + highScores.get(player));
        }
        scoreBoard.setItems(scores);
    }

    private void gameOverHandele() {
        if (game.isGameOver()){
            gameStatus.setText("New Game");
            updateScoreBoard();
        }
    }

    private void soundEffectHandle() {
        if (game.soundEffectHandle()) {
            mediaPlayer.play();
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    // AnimationTimer runs once every frame
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

            soundEffectHandle();
            game.gameLoop(nanotime);
            updateCurrentScore();
            updateLivesLeft();
            gameOverHandele();

            // renders all the objects on screen
            game.getSprites().stream().forEach((sprite) -> {
                renderSprite(sprite);
            });

            // controls spaceship actions
            spaceshipAction(game.getSpaceship());

        }
    };
}
