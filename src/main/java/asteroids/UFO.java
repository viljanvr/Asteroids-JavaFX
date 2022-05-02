package asteroids;

import java.util.Collection;

public class UFO extends Sprite {

    public UFO() {
        super(-30, 100, 0.4, 0, 50, 50, "asteroids/ufo.png");
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(
                sprite -> ((sprite instanceof Laser && ((Laser) sprite).getFriendly()) || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.overlapsSprite(sprite));
    }

    public Laser shootTowardSpaceship(Double x1, Double y1) {
        double result = Math.atan2((y1 - this.y1), (-this.x1 + x1));
        return new Laser(getPosX(), getPosY(), 1.5, result, false);
    }

}
