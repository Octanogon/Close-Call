package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.RequiresApi;

public class Space extends View {

    private Earth earth;
    private Paint asteroidPaint;

    private int currentX = 0;
    private int currentY = 0;

    private int minX = -1000;
    private int maxX = 1000;
    private int minY = -1000;
    private int maxY = 1000;

    private int SCALE = 32;

    private int time_scale = 1;
    private long current_time = 0;

    private float zoom = 1;
    private float zoomx = 0;
    private float zoomy = 0;

    private int currentWidth;
    private int currentHeight;

    private int centerx;
    private int centery;

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;


    private Scroller scroller;
    private ValueAnimator scrollAnimator;

    private int FRAME_RATE = 120;
    private ValueAnimator frameUpdater;

    private AsteroidList asteroids;

    public Space(Context context, AttributeSet attrs) {
        super(context, attrs);

        asteroids = new AsteroidList();
        init();

    }

    private void init() {

        earth = new Earth(BitmapFactory.decodeResource(getResources(), R.drawable.earth));

        frameUpdater = ValueAnimator.ofInt(0);
        frameUpdater.setDuration(1000/FRAME_RATE);

        frameUpdater.setRepeatCount(ValueAnimator.INFINITE);
        frameUpdater.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                current_time += time_scale;
                invalidate();
            }
        });

        frameUpdater.start();

        setBackgroundColor(Color.BLACK);

        asteroidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        asteroidPaint.setStyle(Paint.Style.FILL);
        asteroidPaint.setColor(Color.GRAY);

        scaleGestureDetector = new ScaleGestureDetector(this.getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {

                        zoom *= detector.getScaleFactor();
                        // Don't let it get too small or large
                        zoom = Math.max(0.1f, Math.min(zoom, 5.0f));

                        zoomx = detector.getFocusX();
                        zoomy = detector.getFocusY();
                        invalidate();
                        return true;
                    }
                });

        gestureDetector = new GestureDetector(this.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        Log.d("TOUCH", "TOUCHED");
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        Log.d("FLING", "onFling: flinged: " + velocityX + " " + velocityY);
                        scroller.fling(0, 0, (int) (velocityX / SCALE), (int) (velocityY / SCALE), minX, maxX, minY, maxY);
                        scrollAnimator.start();
                        postInvalidate();

                        return true;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        Log.d("Scroll", "x: " + distanceX + " y: " + distanceY);
                        updateCenter(-(int) distanceX, -(int) distanceY);
                        postInvalidate();
                        return true;
                    }


                });

        Log.d("SPACE", "Done gesture");
        scroller = new Scroller(getContext(), null, true);
        scrollAnimator = ValueAnimator.ofFloat(0, 1);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (!scroller.isFinished()) {
                    scroller.computeScrollOffset();
                    updateCenter(scroller.getCurrX(), scroller.getCurrY());
                } else {
                    scrollAnimator.cancel();
                }
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = gestureDetector.onTouchEvent(event);

        scaleGestureDetector.onTouchEvent(event);

        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                result = true;
            }
        }
        return result;
    }


    private void updateCenter(int x, int y) {
        Log.d("CENTER", "Center update");
        centerx += x;
        centery += y;

        postInvalidate();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Zoom the canvas
        canvas.save();
        canvas.scale(zoom, zoom, zoomx, zoomy);

        // Draw earth
        drawEarth(canvas);

        // Draw the asteroids

        for (Asteroid a : asteroids.getAsteroids()) {

            a.updateTimeAndDistance(current_time);

            // Determine the x and y coordinates of the asteroid

            float x = (float) (centerx + ((a.getDistanceFromEarth() * Math.sin(a.getAngleFromEarth())) / 500));
            float y = (float) (centery - ((a.getDistanceFromEarth() * Math.cos(a.getAngleFromEarth())) / 500));

            canvas.save();
            canvas.rotate(a.getRotationAngleInDegrees(), x, y);
            canvas.drawOval(x - a.getWidth()/2, y - a.getHeight()/2, x + a.getWidth()/2, y + a.getHeight()/2, asteroidPaint);
            canvas.restore();
        }

        canvas.restore();

    }

    private void drawEarth(Canvas canvas)
    {
        earth.draw(canvas, centerx, centery);
        //canvas.drawCircle(centerx, centery, earthRadius, earthPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        currentHeight = h;
        currentWidth = w;

        centerx = currentWidth / 2;
        centery = currentHeight / 2;


    }
}
