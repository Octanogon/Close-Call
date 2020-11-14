package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.animation.LinearInterpolator;

public class Asteroid {
    /* Represents an asteroid */

    private float distanceFromEarth;
    private float angleFromEarth; // As a bearing
    private float width;
    private float height;

    private float rotationAngle = 0;

    private ValueAnimator rotationAnimator;

    public Asteroid(float dist, float angle, float w, float h) {

        distanceFromEarth = dist;
        angleFromEarth = angle;
        width = w;
        height = h;

        rotationAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);

        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        long duration = (long) (1000 + (Math.random() * 8000));
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();


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
