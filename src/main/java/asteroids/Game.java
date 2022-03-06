package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private Spaceship spaceship;
    private Collection<Sprite> sprites = new ArrayList<>();
    private int score = 0;
    private int lives = 3;
    private long lastAsteroidSpawnTime = 0;

    public Game() {
        spaceship = new Spaceship();
        sprites.add(spaceship); // why doesn't new Spaceship work here??
    }

    public void gameLoop(long nanotime) {

        // updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });

        //Removes lazer out of bounds
        //Removes objects when colliding (+ gives points and spawns dwarf asteroids)
        sprites = sprites.stream()
                .flatMap(sprite -> {
                    
                    if ((sprite instanceof Laser && !((Laser)sprite).checkOutOfBound() || !(sprite instanceof Laser)) && !sprite.checkCollision(sprites)){
                        return Stream.of(sprite);
                    }
                    else if (sprite instanceof Asteroid &&((Asteroid) sprite).isNormal()) {
                        incrementScore(20);
                        return ((Asteroid) sprite).dwarfAsteroidsBirthed().stream();
                    }
                    else if (sprite instanceof Asteroid) {
                        incrementScore(10); 
                    }
                    return null;
                }).collect(Collectors.toList());

        System.out.println(sprites.toString());

        // decreases number of lives when hitting asteroid, and spawns new spaceship if you have more lives left
        if (!doesSpaceshipExist() && lives > 0) {
            spaceship = new Spaceship();
            sprites.add(spaceship);
            lives -= 1;
        }

        // spawns and asteroid every four seconds (4 000 000 000 in nanoseconds)
        if (nanotime > lastAsteroidSpawnTime + 4000000000l) {
            sprites.add(new Asteroid(true));
            lastAsteroidSpawnTime = nanotime;
        }

    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int score) {
        this.score += score;

    }

    public Spaceship getSpaceship() {
        return spaceship;
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

    private boolean doesSpaceshipExist() {
        return sprites.stream().anyMatch(sprite -> sprite instanceof Spaceship);
    }

    public boolean isGameOver() {
        return lives == 0 && !doesSpaceshipExist();
    }

}
