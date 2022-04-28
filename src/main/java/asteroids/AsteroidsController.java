package asteroids;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class AsteroidsController {

    public static final int CANVASWIDTH = 800, CANVASHEIGHT = 600;
    private Timer timer;
    private Game game;
    private GraphicsContext gc;
    private boolean UPpressed = false, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true;
    private Media sound;
    private MediaPlayer mediaPlayer;
    private ScoreBoard scoreBoard;

    @FXML
    private Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

    @FXML
    private Text currentScore, livesLeft, saveInfoText, scoreTextLarge, scoreTextSmall;

    @FXML
    private Button saveButton, dontSaveButton, newGameButton;

    @FXML
    private Pane savePane, gameOverPane;

    @FXML
    private ListView<String> scoreBoardList;

    @FXML
    private TextField playerName;

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
        scoreBoard = new ScoreBoard("score_saves");

        // Hide end of game screen.
        gameOverPane.setVisible(false);

        // loads scoreboard from file and updates view
        updateScoreBoard();

        // start rendering
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVASWIDTH, CANVASHEIGHT);

        // starts AnimationTimer
        timer.start();
    }

    // AnimationTimer runs once every frame
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CANVASWIDTH, CANVASHEIGHT);

            game.gameLoop(nanotime);
            soundEffectHandle();
            updateCurrentScore();
            updateLivesLeft();
            gameOverHandel();

            // renders all the objects on screen
            game.getSprites().stream().forEach((sprite) -> {
                renderSprite(sprite);
            });

            // controls spaceship actions
            spaceshipAction(game.getSpaceship());
        }
    };

    private void renderSprite(Sprite sprite) {
        gc.save();

        // Places the image on the correct coordinate
        gc.translate(sprite.getPosX(), sprite.getPosY());

        // Rotates the image
        if (sprite instanceof Spaceship) {
            gc.translate(sprite.getImageWidth() / 2, sprite.getImageHeight() / 2);
            gc.rotate(Math.toDegrees(((Spaceship) sprite).getRotation()));
            gc.translate(-sprite.getImageWidth() / 2, -sprite.getImageHeight() / 2);
        }

        // Draws the image
        gc.drawImage(new Image(sprite.getImageURL()), 0, 0);
        gc.restore();
    }

    private void soundEffectHandle() {
        if (game.soundEffectHandle()) {
            mediaPlayer.play();
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    private void updateCurrentScore() {
        currentScore.setText("Score: " + game.getScore());
    }

    private void updateLivesLeft() {
        livesLeft.setText(game.getLives() + " lives left");
    }

    // Shows the end of game screen when player has lost all of their lives.
    private void gameOverHandel() {
        if (!gameOverPane.isVisible() && game.isGameOver()) {
            gameOverPane.setVisible(true);
            newGameButton.setVisible(false);
            savePane.setVisible(true);

            scoreTextLarge.setText(game.getScore() > scoreBoard.getHighScore() ? "New Highscore!" : "Game over!");
            scoreTextSmall.setText("Score: " + game.getScore());
        }
    }

    private void updateScoreBoard() {
        scoreBoardList.setItems(scoreBoard.getScores().stream()
                .limit(18)
                .map(element -> scoreBoard.getScores().indexOf(element) + 1 + ". " + element.getKey() + ": "
                        + element.getValue())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @FXML
    private void handleSave() {
        scoreBoard.addScore(playerName.getText().trim(), game.getScore());
        updateScoreBoard();
        savePane.setVisible(false);
        newGameButton.setVisible(true);
    }

    @FXML
    private void handleDontSave() {
        savePane.setVisible(false);
        newGameButton.setVisible(true);
    }

    @FXML
    private void startNewGame() {
        game = new Game();
        gameOverPane.setVisible(false);
    }

    @FXML
    private void playerNameInputChanged() {
        int textInputLength = playerName.getText().trim().length();

        if (textInputLength == 0) {
            saveInfoText.setText("Enter playername to save score");
            saveInfoText.setFill(Color.WHITE);
            saveButton.setDisable(true);
        } else if (textInputLength > 16) {
            saveInfoText.setText("Name cannot exceed 16 characters");
            saveInfoText.setFill(Color.RED);
            saveButton.setDisable(true);
        } else {
            saveInfoText.setText("Enter playername to save score");
            saveInfoText.setFill(Color.WHITE);
            saveButton.setDisable(false);
        }
    }

    private void spaceshipAction(Spaceship spaceship) {
        if (this.UPpressed)
            spaceship.thrust();
        if (this.LEFTpressed)
            spaceship.rotateLeft();
        if (this.RIGHTpressed)
            spaceship.rotateRight();
        if (this.SPACEpressed && this.SPACEreleased) {
            game.getSprites().add(spaceship.shoot());
            this.SPACEreleased = false;
        }
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> UPpressed = true;
            case LEFT -> LEFTpressed = true;
            case RIGHT -> RIGHTpressed = true;
            case SPACE -> SPACEpressed = true;
            default -> {
            }
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> UPpressed = false;
            case LEFT -> LEFTpressed = false;
            case RIGHT -> RIGHTpressed = false;
            case SPACE -> {
                SPACEpressed = false;
                SPACEreleased = true;
            }
            default -> {
            }
        }
    }
}
