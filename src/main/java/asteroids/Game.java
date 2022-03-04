package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

        // saves the asteroid that has collided this frame or else return null
        Sprite sp = sprites.stream().filter(sprite -> sprite instanceof Asteroid && sprite.checkCollision(sprites)
                && ((Asteroid) sprite).isNormal())
                .findAny().orElse(null);

        // removes lasers that are out of bound
        // removes objects when they collide (and gives points if you shoot an asteroid)
        setSprites(sprites.stream()
                .filter(sprite -> !collisionHandler(sprite) && (!(sprite instanceof Laser)
                        || ((sprite instanceof Laser) && !((Laser) sprite).checkOutOfBound())))
                .collect(Collectors.toList()));

        // add dward_asteroids if this frame has a collided astroid
        if (sp != null)
            ((Asteroid) sp).dwarfAsteroidsBirthed().stream().forEach(as -> sprites.add(as));

        // updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });

        // decreases number of lives when hitting asteroid, and spawns new spaceship if
        // you have more lives left
        if (!doesSpaceshipExist() && lives > 0) {
            spaceship = new Spaceship();
            sprites.add(spaceship);
            lives -= 1;
        }

        // spawns and asteroid every three seconds (3 000 000 000 in nanoseconds)
        if (nanotime > lastAsteroidSpawnTime + 3000000000l) {
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

    private Boolean collisionHandler(Sprite sprite) {
        if (sprite.checkCollision(sprites)) {
            if (sprite instanceof Asteroid && ((Asteroid) sprite).isNormal())
                incrementScore(20);
            else if (sprite instanceof Asteroid)
                incrementScore(10);
            return true;
        }
        return false;
    }

    private boolean doesSpaceshipExist() {
        return sprites.stream().anyMatch(sprite -> sprite instanceof Spaceship);
    }

    public boolean isGameOver() {
        return lives == 0 && !doesSpaceshipExist();
    }

}
