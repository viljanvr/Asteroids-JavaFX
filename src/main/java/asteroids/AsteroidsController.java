package asteroids;

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

public class AsteroidsController {
    public Timer timer;
    public Spaceship spaceship;
    public Spaceship s2;
    Image spaceshipImage;
    Collection<Sprite> sprites = new ArrayList<>();

    @FXML
    public Canvas canvas = new Canvas(800, 600);
    public GraphicsContext gc;

    // @FXML
    // private ListView<String> scoreBoard;

    public void initialize() {
        timer = new Timer();

        spaceship = new Spaceship(0, 0, new Vector(1, 1));
        s2 = new Spaceship(40, 40, new Vector(4 / 2, 1 / 2));
        spaceshipImage = new Image(spaceship.getImageURL());
        spaceship.setImageSize((int) spaceshipImage.getWidth(), (int) spaceshipImage.getHeight());
        spaceship.setRotation(Math.PI / 4);
        s2.setImageSize((int) spaceshipImage.getWidth(), (int) spaceshipImage.getHeight());
        s2.setRotation(Math.PI / 4);
        sprites.add(spaceship);
        sprites.add(s2);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        // starter AnimationTimer
        timer.start();

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
        gc.drawImage(this.spaceshipImage, 0, 0);
        gc.restore();
    }

    // AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            sprites.stream().forEach((sprite) -> {
                renderSprite(sprite);
                sprite.updatePosition();
            });
        }

    };
}
