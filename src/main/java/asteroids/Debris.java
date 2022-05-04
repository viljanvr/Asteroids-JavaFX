package asteroids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Debris extends Sprite {

    private long timeOfDeath;
    private double rotation;

    private Debris(boolean long_debris, Double x1, Double y1, long spawnTime) {
        super(x1, y1, 0.5, Math.random() * 6.28, long_debris ? 33 : 4, long_debris ? 3 : 4,
                long_debris ? "asteroids/debris_long.png" : "asteroids/debris_small.png");
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

    public static List<Sprite> creatDebris(boolean long_debris, Double x1, Double y1, long spawnTime) {
        List<Sprite> debris = new ArrayList<>();
        for (int i = long_debris ? 2 : 0; i < 5; i++) {
            debris.add(new Debris(long_debris, x1, y1, spawnTime));
        }
        return debris;
    }

}
