# Asteroids

<p>
    <figure align="center">
        <img src="pictures/AsteroidsGame.png" alt="Asteroids game" width="1000"/>
    </figure>
</p>

**This is a recreation of the original arcade game Asteroids, released by Atari in 1979. The objective of the game is to steer a spaceship and to get as many point as possible by shooting incomming asteroids and UFO's. If you crash into an asteroid or UFO, or if you get shot by an UFO, you'll lose one life. You have a total of tre lives before the game is over. Shooting an asteroid will give you 20 points, and the asteroid will splitt into three new small asteroids, which gives you 10 points each if destroyed. Shooting an UFO gives you 50 points. When you have lost all your lives you can save your score on a leaderboard along side your player name. The leaderboard is always visible on the right side of the screen. The controlls in this game are relativly simple: Use arrow-up or w to thrust. Use arrow-left and arrow-right or a and d to steer left and right. Use space to shoot lasers. In addition the game includes settings where you can change the difficulty and adjust the volume.**

The game is built using Java and JavaFX. The game using a modell-view-controller design pattern. The application consists of two controllers, the main one beeing [AsteroidsController.java](src/main/java/asteroids/Controllers/AsteroidsController.java). The [MenuController.java](src/main/java/asteroids/Controllers/MenuController.java) is for the menu overlay appearing before or after a game. The modell consists of a [Game-class](src/main/java/asteroids/Game.java) handling the game logic. The [Spaceship](src/main/java/asteroids/Spaceship.java)-, [Asteroid](src/main/java/asteroids/Asteroid.java)-, [Laser](src/main/java/asteroids/Laser.java)-, [UFO](src/main/java/asteroids/UFO.java)- and [Debris](src/main/java/asteroids/Debris.java)-classes implements the abstract [Sprite-class](src/main/java/asteroids/Sprite.java). In addition there is one [ScoreBoard-class](src/main/java/asteroids/ScoreBoard.java) that saves all score to a text-file and a [Settings-class](src/main/java/asteroids/Settings.java) that saves user settings to a text-file.

You can launch the game by running [AsteroidsApp.java](src/main/java/asteroids/AsteroidsApp.java).

---

## Screenshots

<p>
    <figure align="center">
        <img src="pictures/NewGameScreen.png" alt="New game screen" width="500"/>
        <figcaption><i>New game menu appearing when starting up the game.</i></figcaption>
    </figure>
</p>
<p>
    <figure align="center">
        <img src="pictures/GameOverScreen.png" alt="Game over screen" width="500"/>
        <figcaption><i>Game over menu where you can save your score with along side a player name.</i></figcaption>
    </figure>
</p>
<p>
    <figure align="center">
        <img src="pictures/SettingsScreen.png" alt="Settings screen" width="500"/>
        <figcaption><i>The game lets you adjust difficulty and audio from the settings. You can also see the controls and a section about the game. </i></figcaption>
    </figure>
</p>
<p>
    <figure align="center">
        <img src="pictures/ParticleEffects.png" alt="Particle effects" width="500"/>
        <figcaption><i>When you shoot an asteroid or a spaceship, it will explode into many small pieces.</i></figcaption>
    </figure>
</p>

_Made by [Arash](https://github.com/Arashfa0301) and [Viljan](https://github.com/viljanvr)._
