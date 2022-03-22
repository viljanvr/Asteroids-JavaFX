package asteroids;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;

public class Asteroid extends Sprite {

    private boolean isNormal;

    public Asteroid(boolean isNormal) {
        super(-64, -64, 54, 55, "asteroids/asteroid.png");
        this.isNormal = isNormal;

        // spawns at the top-side of the canvas
        if (Math.random() < 0.5)
            setPosY(Math.random() * AsteroidsController.CanvasHeight);
        // spawns at the left-side of the canvas
        else
            setPosX(Math.random() * AsteroidsController.CanvasWidth);
        getVelocity().setLength(1);
        getVelocity().setAngle(Math.random() * 6.28);
    }

    private Asteroid birthDwarfAsteroid(int x1, int y1) {
        Asteroid dwarfAsteroid = new Asteroid(false);
        dwarfAsteroid.setPosX(x1);
        dwarfAsteroid.setPosY(y1);
        dwarfAsteroid.setImageURL("asteroids/dwarf_asteroid.png");
        dwarfAsteroid.setImageSize(37, 38);
        dwarfAsteroid.getVelocity().setLength(1.4);
        return dwarfAsteroid;
    }

    public Collection<Sprite> dwarfAsteroidsBirthed() {
        Collection<Sprite> list = new ArrayList<>();
        list.add(birthDwarfAsteroid((int) this.getPosX(), (int) this.getPosY()));
        list.add(birthDwarfAsteroid((int) this.getPosX(), (int) this.getPosY()));
        list.add(birthDwarfAsteroid((int) this.getPosX(), (int) this.getPosY()));
        return list;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        wrap();
    }

    public Boolean checkCollision(Collection<Sprite> list) {
        return list.stream().filter(sprite -> (sprite instanceof Laser || sprite instanceof Spaceship))
                .anyMatch(sprite -> this.contains(sprite));
    }

    public boolean isNormal() {
        return isNormal;
    }

}
