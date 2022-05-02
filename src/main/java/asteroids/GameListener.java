package asteroids;

public interface GameListener {

    public void livesLeftChanged(int livedLeft);

    public void gameOver();

    public void spirteCollided();

    public void scoreChanged(int newScore);

}
