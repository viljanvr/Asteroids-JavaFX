package asteroids;

import java.util.ArrayList;
import java.util.Collection;

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
    public Timer timer;
    public Spaceship spaceship;
    public Spaceship s2;
    public Lazer lazer;
    Image spaceshipImage;
    Collection<Sprite> sprites = new ArrayList<>();

    private boolean UPpressed = false;
    private boolean DOWNpressed = false;
    private boolean DOWNreleased = true;
    private boolean LEFTpressed = false;
    private boolean RIGHTpressed = false;
    private boolean SPACEpressed = false;
    private boolean SPACEreleased = true;

    @FXML
    private AnchorPane background;

    @FXML
    public Pane gameArea;

    @FXML
    public Canvas canvas = new Canvas(800, 600);
    public GraphicsContext gc;

    @FXML
    private ListView<String> scoreBoard;

    public void initialize() {
        timer = new Timer();

        spaceship = new Spaceship(200, 200);
        System.out.println("[" + spaceship.getPosX() + ", " + spaceship.getPosY() + "\n" + spaceship.getX2() + ", "
                + spaceship.getY2() + "]");
        sprites.add(spaceship);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        // starter AnimationTimer
        timer.start();

    }

    @FXML
    public void keyPressed(KeyEvent event) {
        // System.out.println("["+spaceship.getPosX()+",
        // "+spaceship.getPosY()+"\n"+spaceship.getX2()+", "+spaceship.getY2()+"]");
        // System.out.println(event.getCode());
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
        // System.out.println("Released "+event.getCode());
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
        if (this.UPpressed) {
            spaceship.thrust();
        }
        if (this.DOWNpressed && this.DOWNreleased) {
            sprites.add(new Asteroid());
            this.DOWNreleased = false;
        }
        if (this.LEFTpressed) {
            spaceship.rotateLeft();
        }
        if (this.RIGHTpressed) {
            spaceship.rotateRight();
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
            gc.fillRect(0, 0, 800, 600);
            Collection<Sprite> spritesCopy = new ArrayList<>(sprites);

            sprites.stream().forEach((sprite) -> {
                sprite.updatePosition();
                renderSprite(sprite);

                if(sprite instanceof Lazer){
                    if (((Lazer) sprite).checkOutOfBound()){
                         spritesCopy.remove(sprite);
                    }
                }
            });

            sprites = spritesCopy;
            System.out.println(sprites.toString());
           
            spaceshipAction(spaceship);

        
            System.out.println(
                    spaceship.velocity.toString() + "(" + spaceship.getPosX() + ", " + spaceship.getX2() + ")");

        }

    };
}
