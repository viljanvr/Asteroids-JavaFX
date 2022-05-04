package asteroids;

public class GameConfig {

    // Speeds
    public static double asteroid_speed;
    public static double dwarfAsteroid_speed;
    public static double ufo_speed;

    // Combat
    public static double laser_speed;
    public static double ufo_aim_accuracy;
    public static long ufo_fire_delay;

    // Movement
    public static double spaceship_acceleration_increase;
    public static double spaceship_acceleration_reduction;
    public static double spaceship_rotationSpeed;

    // SpawnTimes
    public static long asteroid_spawntime;
    public static long ufo_spawntime;

    public GameConfig(boolean difficulty) {

        // Speeds
        asteroid_speed = difficulty ? 1.3 : 1;
        dwarfAsteroid_speed = difficulty ? 1.6 : 1.4;
        ufo_speed = difficulty ? 1.3 : 1;

        // Combat
        laser_speed = difficulty ? 4 : 4;
        ufo_aim_accuracy = difficulty ? Math.PI / 12 : Math.PI / 12;
        ufo_fire_delay = difficulty ? 1500000000l : 2500000000l;

        // Movement
        spaceship_acceleration_increase = difficulty ? 0.15 : 0.18;
        spaceship_acceleration_reduction = difficulty ? -0.02 : -0.02;
        spaceship_rotationSpeed = difficulty ? Math.PI / 45 : Math.PI / 45;

        // SpawnTimes
        asteroid_spawntime = difficulty ? 4000000000l : 5000000000l;
        ufo_spawntime = difficulty ? 7000000000l : 9000000000l;
    }

}
