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

        asteroids[0] = new Asteroid(1000000, (float) ((30.0/360.0) * (2 * Math.PI)), 100, 90, 40000, 10000);
        asteroids[1] = new Asteroid(900000, (float) ((79.0/360.0) * (2 * Math.PI)), 50, 100, 20000, 20000);
        asteroids[2] = new Asteroid(2000000, (float) ((120.0/360.0) * (2 * Math.PI)), 20, 35, 1000000, 10000);
        asteroids[3] = new Asteroid(1500000, (float) ((240.0/360.0) * (2 * Math.PI)), 40, 100, 100000, 50000);
        asteroids[4] = new Asteroid(1200000, (float) ((340.0/360.0) * (2 * Math.PI)), 10, 50, 160000, 40000);
    }

    public Asteroid[] getAsteroids() {
        return asteroids;
    }
}
