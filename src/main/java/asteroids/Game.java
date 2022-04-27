package asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {

    private Spaceship spaceship = new Spaceship();
    private List<Sprite> sprites = new ArrayList<>();
    private int score = 0, lives = 3;
    private long lastAsteroidSpawnTime = 0;

    public Game() {

        // Spawns in inital asteroid and spaceship
        sprites.add(spaceship);
        sprites.add(new Asteroid());
    }

    public void gameLoop(long nanotime) {

        sprites = sprites.stream()
                .flatMap(sprite -> {
                    //Removes spaceship/sprites that have collided and lasers that are out of bounds by not returning them.
                    if (((sprite instanceof Laser && !((Laser) sprite).checkOutOfBound()) || !(sprite instanceof Laser))
                            && !sprite.checkCollision(sprites)) {
                        return Stream.of(sprite);
                    } 
                    //Increases the score wtih 20 points when a large asteroid collides with a laser or spaceship and return 3 new small asteroids.
                    else if (sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()) {
                        incrementScore(20);
                        return ((Asteroid) sprite).splitLargeAsteroid().stream();
                    } 
                    //Increases the score wtih 10 points when a small asteroid collides with a laser or spaceship.
                    else if (sprite instanceof Asteroid) {
                        incrementScore(10);
                    }
                    return null;
                }).collect(Collectors.toList());

        // Decreases number of lives when hitting asteroid, and spawns new spaceship if
        // you have more lives left and if no asteroids are in vacinity of the spawning
        // area.
        if (!sprites.contains(spaceship) && lives > 0
                && !sprites.stream().filter(sprite -> sprite instanceof Asteroid)
                        .anyMatch(sprite -> sprite.isInsideRectangle(350, 250, 450, 350))) {
            spaceship = new Spaceship();
            sprites.add(spaceship);
            lives -= 1;
        }

        // Spawns and asteroid every six seconds (6 000 000 000 in nanoseconds)
        if (nanotime >= lastAsteroidSpawnTime + 6000000000l && !isGameOver()) {
            sprites.add(new Asteroid());
            lastAsteroidSpawnTime = nanotime;
        }

        // Updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });
    }

    public boolean soundEffectHandle() {
        return sprites.stream().anyMatch(sprite -> sprite instanceof Asteroid && sprite.checkCollision(sprites));
    }

    public int getScore() {
        return score;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return lives == 0 && !sprites.contains(spaceship);
    }

    private void incrementScore(int score) {
        this.score += score;
    }

}
