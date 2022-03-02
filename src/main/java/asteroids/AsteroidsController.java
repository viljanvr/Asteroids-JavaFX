package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AsteroidsController {
    public static final int CanvasWidth = 800,
            CanvasHeight = 600;

    public Timer timer;
    public Spaceship spaceship;
    Collection<Sprite> sprites = new ArrayList<>();

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

    // initializes the game
    public void initialize() {
        timer = new Timer();

        spaceship = new Spaceship(200, 200);
        sprites.add(spaceship);

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
            sprites.add(new Asteroid());
            this.DOWNreleased = false;
        }

        if (this.SPACEpressed && this.SPACEreleased) {
            sprites.add(spaceship.shoot());
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

    // AnimationTimer kjører en gang hver frame.
    private class Timer extends AnimationTimer {

        @Override
        public void handle(long nanotime) {
            gc.fillRect(0, 0, CanvasWidth, CanvasHeight);

            // removes lazers that are out of bound
            sprites = sprites.stream()
                    .filter(sprite -> !(sprite instanceof Lazer)
                            || ((sprite instanceof Lazer) && !((Lazer) sprite).checkOutOfBound()))
                    .collect(Collectors.toList());

            // removes collided sprites
            sprites = sprites.stream()
                    .filter(sprite -> !sprite.checkCollision(sprites))
                    .collect(Collectors.toList());

            // updates the position of all the sprites
            sprites.stream().forEach((sprite) -> {
                sprite.updatePosition();
                renderSprite(sprite);
            });

            // initializes arrow key functionalities
            spaceshipAction(spaceship);

            System.out.println(
                    spaceship.velocity.toString() + "(" + spaceship.getPosX() + ", " + spaceship.getX2() + ")");

        }
    };
}
