package com.octanogon.closecall;

public class Asteroid {
    /* Represents an asteroid */

    private float distanceFromEarth;
    private float angleFromEarth; // As a bearing
    private float radius;

    public Asteroid(float dist, float angle, float rad) {

        distanceFromEarth = dist;
        angleFromEarth = angle;
        radius = rad;

    }

    public float getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public float getAngleFromEarth() {
        return angleFromEarth;
    }

    public float getRadius() {
        return radius;
    }


}
