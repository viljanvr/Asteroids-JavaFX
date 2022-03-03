package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Game {
    private Spaceship spaceship;
    private Collection<Sprite> sprites = new ArrayList<>();
    private int score;
    private int lives;
    
    public Game(){
        spaceship = new Spaceship(200, 200);
        sprites.add(spaceship);
    }


    public void gameLoop(){
        // removes lasers that are out of bound
        sprites = sprites.stream()
        .filter(sprite -> !(sprite instanceof Laser)
                || ((sprite instanceof Laser) && !((Laser) sprite).checkOutOfBound()))
        .collect(Collectors.toList());
        
        // removes collided sprites
        sprites = sprites.stream()
                .filter(sprite -> !sprite.checkCollision(sprites))
                .collect(Collectors.toList());

        // updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });

    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score += 20;

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
    
}

    
    

