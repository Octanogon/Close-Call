package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.animation.LinearInterpolator;

public class Asteroid {
    /* Represents an asteroid */

    private static final double EARTH_MASS = 6e24;
    private static final double BIG_G = 6.7e-11;

    private float distanceFromEarth; // in m
    private float angleFromEarth; // As a bearing
    private float width;
    private float height;
    private float mass;

    private float closestDistance; // in m
    private float closestApproachVelocity; // in m/s

    private float conservedh; // h = r^2 / dot{theta}

    private float rotationAngle = 0;

    private long previousTime = 0;

    private ValueAnimator rotationAnimator;

    public Asteroid(float dist, float angle, float w, float h, float closestDist, float closestApproachVel) {

        distanceFromEarth = dist;
        angleFromEarth = angle;
        width = w;
        height = h;
        closestDistance = closestDist;
        closestApproachVelocity = closestApproachVel;

        conservedh = (closestDist * closestDist) * (closestApproachVel/closestDist);

        rotationAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);

        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        long duration = (long) (1000 + (Math.random() * 8000));
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();


    }

    public void updateTimeAndDistance(long time)
    {
        long elapsedTime = previousTime - time;

        previousTime = (long) time;

        // r^2 \dot{\theta} = h

        double theta_dot = conservedh / (distanceFromEarth * distanceFromEarth);
        angleFromEarth += theta_dot * elapsedTime;

        // Now \ddot{r} = h^2/r^3 + MG/r^2


        double r_ddot = (conservedh*conservedh)/(distanceFromEarth*distanceFromEarth*distanceFromEarth) - (EARTH_MASS * BIG_G)/(distanceFromEarth * distanceFromEarth);
        double r_change = r_ddot * elapsedTime * elapsedTime;

        distanceFromEarth += r_change;

        Log.d("AST", "distance: " +distanceFromEarth + " theta: " + angleFromEarth);


    }

    public float getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public float getAngleFromEarth() {
        return angleFromEarth;
    }

    public float getWidth() {return width;}

    public float getHeight() {return height;}

    public float getRotationAngleInDegrees() {return (float) (((float) rotationAnimator.getAnimatedValue()) / (2*Math.PI)) * 360;}



}
