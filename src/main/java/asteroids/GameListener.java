package asteroids;

public interface GameListener {

    public void livesLeftChanged(int livedLeft);

    public void gameOver();

    public void asteroidCollided();

    public void scoreChanged(int newScore);

}
