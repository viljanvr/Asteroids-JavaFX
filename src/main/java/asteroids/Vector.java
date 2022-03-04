package asteroids;

import java.lang.Math;

public class Vector {
    private double x, y;

    public Vector(double x, double y) {
        setXY(x, y);
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void setAngle(double angle) {
        this.setXY(Math.cos(angle) * getLength(), Math.sin(angle) * getLength());
    }

    public void setLength(double length) {
        if (getLength() == 0) {
            setXY(length, 0);
        } else {
            setXY(x / getLength() * length, y / getLength() * length);
        }
    }

    public void addXY(double deltaX, double deltaY) {
        setXY(x + deltaX, y + deltaY);
    }

}
