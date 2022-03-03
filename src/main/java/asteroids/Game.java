package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Game {
    private Spaceship spaceship;
    private Collection<Sprite> sprites = new ArrayList<>();
    private int score = 0;
    private int lives = 3;

    public Game() {
        spaceship = new Spaceship(200, 200);
        sprites.add(spaceship);
    }

    public void gameLoop() {
        // removes lasers that are out of bound
        sprites = sprites.stream()
        .filter(sprite -> !(sprite instanceof Laser)
                || ((sprite instanceof Laser) && !((Laser) sprite).checkOutOfBound()))
        .collect(Collectors.toList());

        //removes objects  when they collide (and gives points if you shoot an asteroid)
        sprites = sprites.stream()
        .filter(sprite -> !collisionHandler(sprite))
        .collect(Collectors.toList());

        // updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });


        if (!sprites.stream().anyMatch(sprite -> sprite instanceof Spaceship) && lives > 1) {
            spaceship = new Spaceship(200, 200);
            sprites.add(spaceship);
            lives -= 1;
        }

    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score += 20;

    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public Collection<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(Collection<Sprite> sprites) {
        this.sprites = sprites;
    }

    public int getLives() {
        return lives;
    }

    private Boolean collisionHandler(Sprite sprite){
        if (sprite.checkCollision(sprites)){
            if (sprite instanceof Asteroid){
                incrementScore();
                System.out.println("Score: "+score);
            }
            return true;
        }
        return false;
    }


    
}

    public boolean isGameOver() {
        return lives == 0;
    }

}
