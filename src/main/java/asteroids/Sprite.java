package asteroids;

public class Sprite {

    private int x1, y1, x2, y2;
    private Vector position;
    private Vector velocity;
    String imagePath;

    public Sprite(int x1, int y1, String imagePath) {
        if (x1 > x2 || y1 > y2 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            throw new IllegalArgumentException("Innvalid inputs for rectangle");
        }
        this.imagePath = imagePath;
        velocity = new Vector(2, 2);
        position = new Vector(x1, y1);
        this.x1 = x1;
        x2 = x1 + 1;
        this.y1 = y1;
        y2 = y2 + 1;
    }

    public int getPosX() {
        return x1;
    }

    public int getPosY() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
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

    public void updatePosition() {
        position.setXY(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        x1 += velocity.getX();
        x2 += velocity.getX();
        y1 += velocity.getY();
        y2 += velocity.getY();
    }

    public static void main(String[] args) {

    }

}
