package com.octanogon.closecall;

public class AsteroidList {
    /* Stores the asteroids */

    Asteroid[] asteroids;

    public AsteroidList() {

        findAsteroids();
    }

    private void findAsteroids() {

        int numberOfAsteroids = 5;

        asteroids = new Asteroid[numberOfAsteroids];

        asteroids[0] = new Asteroid(300, (float) ((30.0/360.0) * (2 * Math.PI)), 100);
        asteroids[1] = new Asteroid(200, (float) ((79.0/360.0) * (2 * Math.PI)), 50);
        asteroids[2] = new Asteroid(500, (float) ((120.0/360.0) * (2 * Math.PI)), 20);
        asteroids[3] = new Asteroid(100, (float) ((240.0/360.0) * (2 * Math.PI)), 40);
        asteroids[4] = new Asteroid(500, (float) ((340.0/360.0) * (2 * Math.PI)), 10);
    }

    public Asteroid[] getAsteroids() {
        return asteroids;
    }
}
