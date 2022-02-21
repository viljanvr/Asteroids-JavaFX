package asteroids;

public class Sprite {

    private int x1, y1, x2, y2;

    public Sprite(int x1, int x2, int y1, int y2) {
        if (x1 > x2 || y1 > y2 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            throw new IllegalArgumentException("Innvalid inputs for rectangle");
        }

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getMinX() {
        return x1;
    }

    public int getMinY() {
        return y1;
    }

    public int getMaxX() {
        return x2;
    }

    public int getMaxY() {
        return y2;
    }

    public boolean contains(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public boolean contains(Sprite sprite) {
        if (x1 < sprite.x2 && sprite.x1 < x2) {
            if (y1 < sprite.y2 && sprite.y1 < y2)
                return true;
            else
                return false;
        } else
            return false;
    }
}
