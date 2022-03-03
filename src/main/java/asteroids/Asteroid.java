package asteroids;

import java.lang.Math;
import java.util.Collection;

public class Asteroid extends Sprite {

    // private Asteroid(int x1, int x2, int imageWidth, int imageHeight, String imageURL) {
    //     super(x1, x2, imageWidth, imageHeight, imageURL);
    // }

    public Asteroid() {
        super(-64, -64, 64, 64, "asteroids/asteroid.png");

        //spawns at the top-side of the canvas
        if (Math.random() < 0.5){
            setPosY(Math.random() * AsteroidsController.CanvasHeight);
        }
        //spawns at the left-side of the canvas
        else {
            setPosX(Math.random() * AsteroidsController.CanvasWidth);
        }
        getVelocity().setLength(1);
        getVelocity().setAngle(Math.random() * 6.28);
        
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> (sprite instanceof Laser || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.contains(sprite));
    }

}
