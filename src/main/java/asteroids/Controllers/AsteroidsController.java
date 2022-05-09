package asteroids.Controllers;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.canvas.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import asteroids.*;

public class AsteroidsController implements GameListener {

    private MenuController menuController = new MenuController();

    public static final int CANVASWIDTH = 800, CANVASHEIGHT = 600;
    private Timer timer;
    private Game game;
    private GraphicsContext gc;
    private boolean UPpressed = false, LEFTpressed = false,
            RIGHTpressed = false, SPACEpressed = false, SPACEreleased = true, difficulty = false;
    private Media collisionSound, laserSoundTrack, hardSoundTrack, normalSoundTrack;
    private MediaPlayer collisionSoundPlayer, laserSoundTrackPlayer, hardSoundTrackPlayer, normalSoundTrackPlayer,
            currentSoundTrackPlayer;
    private ScoreBoard scoreBoard;

    @FXML
    private Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

    @FXML
    private Text currentScore, livesLeft;

    @FXML
    private BorderPane menuContainer;

    @FXML
    private ListView<String> scoreBoardList;

    private Pane newGamePane, settingsPane, controlsPane, aboutPane, audioPane, difficultyPane;

    // @FXML
    // private SettingsController settingsWindowController;

    // initializes the game
    public void initialize() {

        // load sound and music
        try {
            collisionSound = new Media(
                    getClass().getClassLoader().getResource("asteroids/audio/boom.mp3").toURI()
                            .toString());
            hardSoundTrack = new Media(
                    getClass().getClassLoader().getResource("asteroids/audio/ripAndTear.mp3").toURI()
                            .toString());
            normalSoundTrack = new Media(
                    getClass().getClassLoader().getResource("asteroids/audio/soundTrack.mp3").toURI()
                            .toString());
            laserSoundTrack = new Media(
                    getClass().getClassLoader().getResource("asteroids/audio/laser.mp3").toURI()
                            .toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        collisionSoundPlayer = new MediaPlayer(collisionSound);
        hardSoundTrackPlayer = new MediaPlayer(hardSoundTrack);
        normalSoundTrackPlayer = new MediaPlayer(normalSoundTrack);
        laserSoundTrackPlayer = new MediaPlayer(laserSoundTrack);
        laserSoundTrackPlayer.setVolume(0.5);

        // starts with soundtrack for normal difficulty.
        initiateSoundTrack(normalSoundTrackPlayer);

        timer = new Timer();
        scoreBoard = new ScoreBoard("saves", "score_saves");

        // loads scoreboard from file and updates view
        updateScoreBoard();

        // start rendering
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVASWIDTH, CANVASHEIGHT);

        // Initalize all menu views
        newGamePane = getMenuPane("/asteroids/FXMLs/NewGameFx.fxml");
        settingsPane = getMenuPane("/asteroids/FXMLs/SettingsFx.fxml");
        controlsPane = getMenuPane("/asteroids/FXMLs/ControlsFx.fxml");
        aboutPane = getMenuPane("/asteroids/FXMLs/AboutFx.fxml");
        audioPane = getMenuPane("/asteroids/FXMLs/AudioFx.fxml");
        difficultyPane = getMenuPane("/asteroids/FXMLs/DifficultyFx.fxml");
        menuController.init(this);

        // Propt user with welcome screen
        changeMenu("NewGameFx.fxml");

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
        } else if (sprite instanceof Debris) {
            gc.translate(sprite.getImageWidth() / 2, sprite.getImageHeight() / 2);
            gc.rotate(Math.toDegrees(((Debris) sprite).getRotation()));
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

    public void addScore(String playerName) {
        scoreBoard.addScore(playerName, game.getScore());
        updateScoreBoard();
    }

    private void initiateSoundTrack(MediaPlayer soundTrack) {
        if (currentSoundTrackPlayer != null)
            currentSoundTrackPlayer.stop();
        currentSoundTrackPlayer = soundTrack;
        currentSoundTrackPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                soundTrack.seek(Duration.ZERO);
            }
        });
        currentSoundTrackPlayer.play();
    }

    public void startNewGame() {
        game = new Game(this, difficulty);
        scoreBoardList.requestFocus();

        // starts AnimationTimer
        timer.start();

    }

    private void spaceshipAction(Spaceship spaceship) {
        if (UPpressed)
            spaceship.thrust();
        if (LEFTpressed)
            spaceship.rotateLeft();
        if (RIGHTpressed)
            spaceship.rotateRight();
        if (SPACEpressed && SPACEreleased && game.doesGameContainSpaceship()) {
            laserSoundTrackPlayer.play();
            laserSoundTrackPlayer.seek(Duration.ZERO);
            game.getSprites().add(spaceship.shoot());
            SPACEreleased = false;
        }
    }

    public void setGameVolume(double gameVolume) {
        collisionSoundPlayer.setVolume(gameVolume);
        laserSoundTrackPlayer.setVolume(gameVolume / 2);
    }

    public void setMusicVolume(double musicVolume) {
        hardSoundTrackPlayer.setVolume(musicVolume);
        normalSoundTrackPlayer.setVolume(musicVolume);
    }

    public double getGameVolume() {
        return collisionSoundPlayer.getVolume() * 100;
    }

    public double getMusicVolume() {
        return hardSoundTrackPlayer.getVolume() * 100;
    }

    public void updateDifficulty(Boolean difficulty) {
        this.difficulty = difficulty;
        if (difficulty)
            initiateSoundTrack(hardSoundTrackPlayer);
        else
            initiateSoundTrack(normalSoundTrackPlayer);
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> UPpressed = true;
            case W -> UPpressed = true;
            case LEFT -> LEFTpressed = true;
            case A -> LEFTpressed = true;
            case RIGHT -> RIGHTpressed = true;
            case D -> RIGHTpressed = true;
            case SPACE -> SPACEpressed = true;
            default -> {
            }
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> UPpressed = false;
            case W -> UPpressed = false;
            case LEFT -> LEFTpressed = false;
            case A -> LEFTpressed = false;
            case RIGHT -> RIGHTpressed = false;
            case D -> RIGHTpressed = false;
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
        UPpressed = false;
        LEFTpressed = false;
        RIGHTpressed = false;

        menuController.gameOver(game.getScore(), scoreBoard.getHighScore());
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

    public void changeMenu(String s) {
        switch (s) {
            case "NewGameFx.fxml" -> menuContainer.setCenter(newGamePane);
            case "SettingsFx.fxml" -> menuContainer.setCenter(settingsPane);
            case "ControlsFx.fxml" -> menuContainer.setCenter(controlsPane);
            case "AboutFx.fxml" -> menuContainer.setCenter(aboutPane);
            case "AudioFx.fxml" -> menuContainer.setCenter(audioPane);
            case "DifficultyFx.fxml" -> menuContainer.setCenter(difficultyPane);
            default -> menuContainer.setCenter(null);
        }
    }

    public Pane getMenuPane(String s) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(s));
        fxmlLoader.setController(menuController);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

}