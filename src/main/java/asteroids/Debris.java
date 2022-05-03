package asteroids;

import java.util.Collection;

public class Debris extends Sprite {

    private long timeOfDeath;
    private double rotation;

    public Debris(double x1, double y1, int IMAGEWIDTH, int IMAGEHEIGHT, String IMAGE_URL, long spawnTime) {
        super(x1, y1, 0.5, Math.random() * 6.28, IMAGEWIDTH, IMAGEHEIGHT, IMAGE_URL);
        this.timeOfDeath = spawnTime + 1000000000l + (long) (1000000000l * Math.random());
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
