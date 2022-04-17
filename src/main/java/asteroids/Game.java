package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {

    private Spaceship spaceship = new Spaceship();
    private Collection<Sprite> sprites = new ArrayList<>();
    private int score = 0, lives = 3;
    private long lastAsteroidSpawnTime = 0;

    public Game() {

        // spawns in inital asteroid and spaceship
        sprites.add(spaceship);
        sprites.add(new Asteroid());
    }

    public void gameLoop(long nanotime) {

        // Removes lazer out of bounds and collided objects
        // gives points and spawns dwarf asteroids
        sprites = sprites.stream()
                .flatMap(sprite -> {
                    if (((sprite instanceof Laser && !((Laser) sprite).checkOutOfBound()) || !(sprite instanceof Laser))
                            && !sprite.checkCollision(sprites)) {
                        return Stream.of(sprite);
                    } else if (sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()) {
                        incrementScore(20);
                        return ((Asteroid) sprite).splitLargeAsteroid().stream();
                    } else if (sprite instanceof Asteroid) {
                        incrementScore(10);
                    }
                    return null;
                }).collect(Collectors.toList());

        // decreases number of lives when hitting asteroid, and spawns new spaceship if
        // you have more lives left and if no asteroids are in vacinity of the spawning
        // area
        if (!sprites.contains(spaceship) && lives > 0
                && !sprites.stream().filter(sprite -> sprite instanceof Asteroid)
                        .anyMatch(sprite -> sprite.isInsideRectangle(350, 250, 450, 350))) {
            spaceship = new Spaceship();
            sprites.add(spaceship);
            lives -= 1;
        }

        // spawns and asteroid every six seconds (6 000 000 000 in nanoseconds)
        if (nanotime > lastAsteroidSpawnTime + 6000000000l && !isGameOver()) {
            sprites.add(new Asteroid());
            lastAsteroidSpawnTime = nanotime;
        }

        // updates the position of all the sprites
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

    public Collection<Sprite> getSprites() {
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
