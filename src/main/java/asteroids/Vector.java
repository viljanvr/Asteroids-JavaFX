package asteroids;

import java.lang.Math;

public class Vector {
    private double x = 0;
    private double y = 0;

    public Vector(double x, double y) {
        setXY(x, y);
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double getAngle() {
        return Math.atan(this.y / this.x);

    }

    public void setAngle(double angle) {
        this.setXY(Math.cos(angle) * this.getLength(), Math.sin(angle) * this.getLength());
    }

    public void setLength(double length) {
        if (this.getLength() == 0) {
            this.setXY(length, 0);
        } else {
            this.setXY(x / this.getLength() * length, y / this.getLength() * length);
        }
    }

    public void multiplyLength(double scalar) {
        this.setXY(this.x * scalar, this.y * scalar);
    }

    public void addVector(Vector vector) {
        setXY(this.x + vector.getX(), this.y + vector.getY());
    }

    public void addXY(double deltaX, double deltaY) {
        setXY(this.x + deltaX, this.y + deltaY);
    }

    public String toString() {
        return "( " + this.x + ", " + this.y + " )";
    }

    public static void main(String[] args) {
        Vector v = new Vector(3.0, 0);

    }

}
