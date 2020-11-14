package com.octanogon.closecall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Space extends View {

    private Paint earthPaint;

    private Paint asteroidPaint;

    private int currentWidth;
    private int currentHeight;

    private int centerx;
    private int centery;


    private int earthRadius = 100;

    private AsteroidList asteroids;

    public Space(Context context, AttributeSet attrs) {
        super(context, attrs);

        asteroids = new AsteroidList();
        init();

    }

    private void init() {
        earthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        earthPaint.setStyle(Paint.Style.FILL);
        earthPaint.setColor(Color.GREEN);

        asteroidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        asteroidPaint.setStyle(Paint.Style.FILL);
        asteroidPaint.setColor(Color.GRAY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(currentWidth/2,currentHeight/2,earthRadius, earthPaint);

        // Draw the asteroids

        for (Asteroid a : asteroids.getAsteroids()) {

            // Determine the x and y coordinates of the asteroid

            float x = (float) (centerx + (a.getDistanceFromEarth() * Math.sin(a.getAngleFromEarth())));
            float y = (float) (centery - (a.getDistanceFromEarth() * Math.cos(a.getAngleFromEarth())));

            canvas.drawCircle(x, y, a.getRadius(), asteroidPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        currentHeight = h;
        currentWidth = w;

        centerx = currentWidth/2;
        centery = currentHeight/2;



    }
}
