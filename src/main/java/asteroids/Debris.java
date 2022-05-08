package asteroids;

import java.util.Collection;

public class Debris extends Sprite {

    private long timeOfDeath;
    private double rotation;

    public Debris(boolean long_debris, Double x1, Double y1, long spawnTime) {
        super(x1, y1, 0.5, Math.random() * 6.28, long_debris ? 33 : 4, long_debris ? 3 : 4,
                long_debris ? "asteroids/images/debris_long.png" : "asteroids/images/debris_small.png");
        timeOfDeath = spawnTime + 1000000000l + (long) (1000000000l * Math.random());
        rotation = Math.random() * 6.28;
    }

    @Override
    public boolean checkCollision(Collection<Sprite> list) {
        return false;
    }

    public boolean shouldDisapear(long currentTime) {
        return currentTime > timeOfDeath;
    }

    public double getRotation() {
        return rotation;
    }

}
