package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.animation.LinearInterpolator;

public class Asteroid {
    /* Represents an asteroid */

    private static final double EARTH_MASS = 5.972e24 * 10;
    private static final double BIG_G = 6.674e-11;



    private double distanceFromEarth; // in m
    private double angleFromEarth; // As a bearing in radians
    private double r_dot; // in m/s
    private double width;
    private double height;
    private double mass;

    // Closest distance in m
    // Closest approach velocity m/s

    private double conservedh; // h = r^2 / dot{theta}

    private double rotationAngle = 0;

    private boolean reversed = false;

    private ValueAnimator rotationAnimator;

    public Asteroid(double x, double y, double xVelocity, double yVelocity)
    {
        x = x*500;
        y = y*500;
        distanceFromEarth = (double) Math.sqrt(x*x + y*y) / 10;
        angleFromEarth = (double) Math.atan2(y, x) + (double) Math.PI/2;

        if (angleFromEarth > Math.PI * 2)
        {
            angleFromEarth -= Math.PI/2;
        }

        if (angleFromEarth < 0)
        {
            angleFromEarth = (double) (Math.PI * 2 + angleFromEarth);
        }

        Log.d("NEW ASTEROID", "dist: " + distanceFromEarth + " angle: " + angleFromEarth);

        width = 10000 + (double) Math.random() * 20;
        height = 10000 + (double) Math.random() * 20;

        double transverseVelocity = (double) (xVelocity * Math.cos(angleFromEarth) + yVelocity * Math.sin(angleFromEarth));

        double theta_dot = (double) transverseVelocity / distanceFromEarth;
        conservedh = (distanceFromEarth * distanceFromEarth) * theta_dot;
        //conservedh = 0;
        // THIS NEEDS TO BE CHANGED TO REFLECT THE VELOCITY

        //double r_dot = - (xVelocity * Math.sin(angleFromEarth) + yVelocity * Math.cos(angleFromEarth)); // Should be correct up to a sign

        rotationAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);

        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        long duration = (long) (1000 + (Math.random() * 8000));
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();

    }

    public Asteroid(double dist, double angle, double w, double h, double closestDist, double closestApproachVel) {

        distanceFromEarth = dist;
        angleFromEarth = angle;
        width = w;
        height = h;
        double closestDistance = closestDist;
        double closestApproachVelocity = closestApproachVel;

        conservedh = (double) (closestDist * closestApproachVel);

        double specificConservedEnergy = (0.5 * closestApproachVel*closestApproachVel) + (BIG_G*EARTH_MASS)/closestDist;

        Log.d("ASTEROID", "Energy: " + specificConservedEnergy);

        double v_squared = (double) 2*(specificConservedEnergy - (BIG_G * EARTH_MASS)/(distanceFromEarth));
        Log.d("ASTEROID", "v_squared: " + v_squared);
        Log.d("ASTEROID", "v_square - other = " + (v_squared - (conservedh*conservedh)/(distanceFromEarth*distanceFromEarth)));
        r_dot = - (double) Math.sqrt(v_squared - (conservedh*conservedh)/(distanceFromEarth*distanceFromEarth));
        Log.d("ASTEROID", "r_dot: " + r_dot);

        rotationAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);

        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        long duration = (long) (1000 + (Math.random() * 8000));
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        // Randomly decide the direction of the asteroid

        if (Math.random() <= 0.5)
        {
            reversed = true;
        }
        rotationAnimator.start();


    }

    public void updateTimeAndDistance(long time_step)
    {
        long elapsedTime = (long) time_step*100;

        //previousTime = (long) time;

        // r^2 \dot{\theta} = h

        double theta_dot = conservedh / (distanceFromEarth * distanceFromEarth);

        angleFromEarth += theta_dot * elapsedTime*3000;



        // Now \ddot{r} = h^2/r^3 - MG/r^2


        double r_ddot = (conservedh*conservedh)/(distanceFromEarth*distanceFromEarth*distanceFromEarth) - (EARTH_MASS * BIG_G)/((distanceFromEarth * distanceFromEarth));

        double r_dot_change = r_ddot * elapsedTime;

        r_dot += r_dot_change;

        double r_change = r_ddot * elapsedTime * elapsedTime * 1000;

        distanceFromEarth += r_change;

        Log.d("AST", "distance: " + distanceFromEarth + " theta: " + angleFromEarth);


    }

    public double getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public double getAngleFromEarth() {

        if (reversed)
        {
            return - angleFromEarth;
        }
        else
        {
            return angleFromEarth;
        }


    }

    public double getWidth() {return width;}

    public double getHeight() {return height;}

    public double getRotationAngleInDegrees() {return (double) (((float) rotationAnimator.getAnimatedValue()) / (2*Math.PI)) * 360;}


    public void kill() {

        distanceFromEarth = 1e15;
    }
}
