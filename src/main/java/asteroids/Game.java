package asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {

    private GameListener gameListener;

    private Spaceship spaceship = new Spaceship();
    private List<Sprite> sprites = new ArrayList<>();
    private int score = 0, lives = 3;
    private long lastAsteroidSpawnTime = 0, lastUFOSpawnTime = 0, lastUFOShootTime = 0, lastSpaceshipSpawnTime = 0;

    public Game(GameListener gameListener, boolean difficulty) {
        this.gameListener = gameListener;
        new GameConfig(false);
        gameListener.scoreChanged(score);
        gameListener.livesLeftChanged(lives);

        // Spawns in inital asteroid and spaceship
        sprites.add(spaceship);
    }

    public void gameLoop(long nanotime) {

        if (sprites.stream().anyMatch(sprite -> sprite.checkCollision(sprites))) {
            gameListener.spirteCollided();
        }

        sprites = sprites.stream()
                .flatMap(sprite -> {
                    // // Removes spaceship/sprites that have collided and lasers that are out of
                    // // bounds by not returning them.
                    // if ((sprite instanceof Laser && !((Laser) sprite).checkOutOfBound()
                    // && !sprite.checkCollision(sprites))
                    // || ((sprite instanceof Asteroid || sprite instanceof Spaceship || sprite
                    // instanceof UFO)
                    // && !sprite.checkCollision(sprites))
                    // || (sprite instanceof Debris && ((Debris) sprite).getAge(nanotime) <
                    // 2500000000l)) {
                    // return Stream.of(sprite);
                    // }
                    // // Increases the score wtih 20 points when a large asteroid collides with a
                    // // laser or spaceship and return 3 new small asteroids.
                    // else if (sprite instanceof Asteroid && ((Asteroid) sprite).isLarge()) {
                    // incrementScore(20);
                    // return ((Asteroid) sprite).splitLargeAsteroid().stream();
                    // }
                    // // Increases the score wtih 10 points when a small asteroid collides with a
                    // // laser or spaceship.
                    // else if (sprite instanceof Asteroid) {
                    // incrementScore(10);
                    // } else if (sprite instanceof UFO) {
                    // incrementScore(50);
                    // } else if (sprite instanceof Spaceship) {
                    // if (lives == 0)
                    // gameListener.gameOver();
                    // return ((Spaceship) sprite).dead(nanotime).stream();
                    // }
                    // return null;

                    // ONLY TEMPORARY, WILL CHANGE
                    // Checks laser
                    if (sprite instanceof Laser
                            && !((Laser) sprite).checkOutOfBound() && !sprite.checkCollision(sprites)) {
                        return Stream.of(sprite);
                    }

                    // Checks asteroids
                    else if (sprite instanceof Asteroid) {
                        if (sprite.checkCollision(sprites)) {
                            if (((Asteroid) sprite).isLarge()) {
                                incrementScore(20);
                                return ((Asteroid) sprite).splitLargeAsteroid(nanotime).stream();
                            } else
                                incrementScore(10);
                            return ((Asteroid) sprite).explode(nanotime).stream();
                        }
                        return Stream.of(sprite);
                    }

                    // Checks Debris
                    else if (sprite instanceof Debris && !((Debris) sprite).shouldDisapear(nanotime)) {
                        return Stream.of(sprite);
                    }

                    // Checks spaceship
                    else if (sprite instanceof Spaceship) {
                        if (sprite.checkCollision(sprites)) {
                            if (lives == 0)
                                gameListener.gameOver();
                            return ((Spaceship) sprite).explode(nanotime).stream();
                        } else
                            return Stream.of(sprite);
                    }

                    // Cheks UFO
                    else if (sprite instanceof UFO) {
                        if (sprite.checkCollision(sprites)) {
                            incrementScore(50);
                            return ((UFO) sprite).explode(nanotime).stream();
                        } else
                            return Stream.of(sprite);
                    }

                    return null;

                }).collect(Collectors.toList());

        // Decreases number of lives when hitting asteroid, and spawns new spaceship if
        // you have more lives left and if no asteroids are in vacinity of the spawning
        // area.
        if (!doesGameContainSpaceship() && lives > 0
                && !sprites.stream().filter(sprite -> sprite instanceof Asteroid)
                        .anyMatch(sprite -> sprite.isInsideRectangle(350, 250, 450, 350))
                && sprites.stream().filter(sprite -> sprite instanceof Debris)
                        .noneMatch(sprite -> sprite.getImageWidth() == 33)) {
            spaceship = new Spaceship();
            sprites.add(spaceship);
            lastSpaceshipSpawnTime = nanotime;
            gameListener.livesLeftChanged(lives -= 1);
        }

        // Spawns asteroids
        if (nanotime >= lastAsteroidSpawnTime + GameConfig.asteroid_spawntime && doesGameContainSpaceship()) {
            sprites.add(new Asteroid());
            lastAsteroidSpawnTime = nanotime;
        }

        // If UFOs exists, shoot a laser(and only if it's more than 3,5
        // seconds since you spawned).
        if (nanotime >= lastUFOShootTime + GameConfig.ufo_fire_delay && !isGameOver()
                && nanotime >= lastSpaceshipSpawnTime + 3500000000l) {
            sprites.addAll(sprites.stream().filter(sprite -> sprite instanceof UFO)
                    .map(sprite -> ((UFO) sprite).shootTowardSpaceship(spaceship.getPosX(), spaceship.getPosY()))
                    .collect(Collectors.toList()));
            lastUFOShootTime = nanotime;

            // 40% chance that any given UFO changes direction.
            sprites.stream().filter(sprite -> sprite instanceof UFO)
                    .forEach(sprite -> {
                        if (Math.random() < 0.4)
                            ((UFO) sprite).changeDirection();
                    });
        }

        // Spawns UFOs
        if (nanotime >= lastUFOSpawnTime + GameConfig.ufo_spawntime && !isGameOver()) {
            sprites.add(new UFO());
            lastUFOSpawnTime = nanotime;
        }

        // Updates the position of all the sprites
        sprites.stream().forEach((sprite) -> {
            sprite.updatePosition();
        });
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
        return lives == 0 && !doesGameContainSpaceship();
    }

    public boolean doesGameContainSpaceship() {
        return sprites.contains(spaceship);
    }

    private void incrementScore(int score) {
        gameListener.scoreChanged(this.score += score);
    }
}
