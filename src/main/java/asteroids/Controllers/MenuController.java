package asteroids;

import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuController {

    @FXML
    private Text saveInfoText, scoreTextSmall, scoreTextLarge;

    @FXML
    private Pane mainMenuPane, savePane;

    @FXML
    private Button newGameButton, settingsButton, saveButton, dontSaveButton, settingsBackButton, controlsBackButton,
            aboutBackButton, audioBackButton, difficultyBackButton;

    @FXML
    private TextField playerName;

    @FXML
    private Slider masterVolumeSlider, gameVolumeSlider, musicVolumeSlider;

    @FXML
    private ChoiceBox<String> difficultySelector;

    AsteroidsController asteroidsController;

    public void init(AsteroidsController asteroidsController) {
        this.asteroidsController = asteroidsController;
        initKeyHandles();

        difficultySelector.getItems().addAll("Normal", "Hard");
        difficultySelector.setValue("Normal");

        difficultySelector.setOnAction(event -> {
            asteroidsController.updateDifficulty((difficultySelector.getValue() == "Hard"));
        });

        // TODO: Whe should read user settings from a save file when we start the game
        masterVolumeSlider.setValue(100);
        gameVolumeSlider.setValue(asteroidsController.getGameVolume());
        musicVolumeSlider.setValue(asteroidsController.getGameVolume());

        masterVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                asteroidsController.setGameVolume(((double) newValue * gameVolumeSlider.getValue() / 10000));
                asteroidsController.setMusicVolume(((double) newValue * musicVolumeSlider.getValue()) / 10000);
            }
        });

        gameVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                asteroidsController.setGameVolume(((double) newValue * masterVolumeSlider.getValue() / 10000));
            }
        });
        musicVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                asteroidsController.setMusicVolume(((double) newValue * masterVolumeSlider.getValue() / 10000));
            }
        });

    }

    @FXML
    public void startNewGame() {
        asteroidsController.changeMenu("");
        asteroidsController.startNewGame();

    }

    @FXML
    private void handleSave() {
        asteroidsController.addScore(playerName.getText().trim());
        handleDontSave();

    }

    @FXML
    private void handleDontSave() {
        savePane.setVisible(false);
        mainMenuPane.setVisible(true);
        newGameButton.setDefaultButton(true);
    }

    @FXML
    private void openSettings() {
        asteroidsController.changeMenu("SettingsFx.fxml");
        settingsBackButton.setCancelButton(true);
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
        } else if (!Pattern.matches("[a-zA-Z0-9_æøåÆØÅ ]*",
                playerName.getText().trim())) {
            saveInfoText.setText("Playername cannot include special characters");
            saveInfoText.setFill(Color.RED);
            saveButton.setDisable(true);
        } else {
            saveInfoText.setText("Enter playername to save score");
            saveInfoText.setFill(Color.WHITE);
            saveButton.setDisable(false);
        }
    }

    @FXML
    private void openNewGame() {
        asteroidsController.changeMenu("NewGameFx.fxml");
    }

    @FXML
    private void openControls() {
        asteroidsController.changeMenu("ControlsFx.fxml");
        controlsBackButton.setCancelButton(true);
    }

    @FXML
    private void openAbout() {
        asteroidsController.changeMenu("AboutFx.fxml");
        aboutBackButton.setCancelButton(true);
    }

    @FXML
    private void openAudio() {
        asteroidsController.changeMenu("AudioFx.fxml");
        audioBackButton.setCancelButton(true);
    }

    @FXML
    private void openDifficulty() {
        asteroidsController.changeMenu("DifficultyFx.fxml");
        difficultyBackButton.setCancelButton(true);
    }

    public void gameOver(int score, int highscore) {
        asteroidsController.changeMenu("NewGameFx.fxml");
        newGameButton.setDefaultButton(false);
        mainMenuPane.setVisible(false);

        savePane.setVisible(true);
        playerName.requestFocus();
        saveButton.setDefaultButton(true);
        dontSaveButton.setCancelButton(true);
        scoreTextLarge.setText(score > highscore ? "New Highscore!" : "Game over!");
        scoreTextSmall.setText("Score: " + score);
    }

    private void initKeyHandles() {
        newGameButton.setOnAction(event -> {
            startNewGame();
        });
        saveButton.setOnAction(event -> {
            handleSave();
        });
        dontSaveButton.setOnAction(event -> {
            handleDontSave();
        });
        settingsBackButton.setOnAction(event -> {
            openNewGame();
        });
        aboutBackButton.setOnAction(event -> {
            openSettings();
        });
        controlsBackButton.setOnAction(event -> {
            openSettings();
        });
        audioBackButton.setOnAction(event -> {
            openSettings();
        });
        difficultyBackButton.setOnAction(event -> {
            openSettings();
        });
    }
}
