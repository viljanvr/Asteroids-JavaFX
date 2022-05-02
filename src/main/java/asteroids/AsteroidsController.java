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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AsteroidsController implements GameListener {

    public static final int CANVASWIDTH = 800, CANVASHEIGHT = 600;
    private Timer timer;
    private Game game;
    private GraphicsContext gc;
    private boolean UPpressed = false, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true;
    private Media collisionSound, soundTrack;
    private MediaPlayer collisionSoundPlayer, soundTrackPlayer;

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
            collisionSound = new Media(
                    getClass().getClassLoader().getResource("asteroids/boom.mp3").toURI().toString());
            soundTrack = new Media(
                    getClass().getClassLoader().getResource("asteroids/soundTrack.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        collisionSoundPlayer = new MediaPlayer(collisionSound);
        soundTrackPlayer = new MediaPlayer(soundTrack);
        soundTrackPlayer.play();
        timer = new Timer();
        scoreBoard = new ScoreBoard("saves", "score_saves");

        // loads scoreboard from file and updates view
        updateScoreBoard();

        // start rendering
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVASWIDTH, CANVASHEIGHT);

        // Propt user with welcome screen
        newGameButton.setVisible(true);
        savePane.setVisible(false);

        scoreTextLarge.setText("Asteroids");
        scoreTextSmall.setText("Press button to start");

    }

    // AnimationTimer runs once every frame
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CANVASWIDTH, CANVASHEIGHT);

            game.gameLoop(nanotime);

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
        game = new Game(this);
        gameOverPane.setVisible(false);

        // starts AnimationTimer
        timer.start();
    }

    @FXML
    private void playerNameInputChanged() {
        int textInputLength = playerName.getText().trim().length();

        if (textInputLength == 0) {
            saveInfoText.setText("Enter playername to save score");
            saveInfoText.setFill(Color.WHITE);
            saveButton.setDisable(true);
        } else if (textInputLength > 14) {
            saveInfoText.setText("Name cannot exceed 14 characters");
            saveInfoText.setFill(Color.RED);
            saveButton.setDisable(true);
        } else if (!Pattern.matches("[a-zA-Z0-9_æøåÆØÅ ]*", playerName.getText().trim())) {
            saveInfoText.setText("Playername cannot include special characters");
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
        if (this.SPACEpressed && this.SPACEreleased && !game.isGameOver()) {
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

    @Override
    public void livesLeftChanged(int livesLeft) {
        this.livesLeft.setText(livesLeft + " lives left");

    }

    @Override
    public void gameOver() {
        gameOverPane.setVisible(true);
        newGameButton.setVisible(false);
        savePane.setVisible(true);

        scoreTextLarge.setText(game.getScore() > scoreBoard.getHighScore() ? "New Highscore!" : "Game over!");
        scoreTextSmall.setText("Score: " + game.getScore());

    }

    @Override
    public void spirteCollided() {
        collisionSoundPlayer.play();
        collisionSoundPlayer.seek(Duration.ZERO);

    }

    @Override
    public void scoreChanged(int newScore) {
        currentScore.setText("Score: " + newScore);
    }
}
