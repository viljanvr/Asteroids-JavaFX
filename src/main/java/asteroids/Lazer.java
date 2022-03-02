package asteroids;

public class Lazer extends Sprite {
    public Lazer(double x1, double y1) {
        super(x1, y1, 8, 8, "asteroids/laser.png");
    }
    

    public boolean checkOutOfBound(){
        if (x1 > 800 || x2 < 0 || y1 > 600 || y2 < 0){
            return true;
        }
        return false;
    }
}
