package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

public class Space extends View {

    private Paint earthPaint;

    private Paint asteroidPaint;

    private int currentX = 0;
    private int currentY = 0;

    private int minX = -1000;
    private int maxX = 1000;
    private int minY = -1000;
    private int maxY = 1000;

    private int SCALE = 32;

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


    private int earthRadius = 100;

    private AsteroidList asteroids;

    public Space(Context context, AttributeSet attrs) {
        super(context, attrs);

        asteroids = new AsteroidList();
        init();

    }

    private void init() {

        setBackgroundColor(Color.BLACK);

        earthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        earthPaint.setStyle(Paint.Style.FILL);
        earthPaint.setColor(Color.GREEN);

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

        canvas.save();
        canvas.scale(zoom, zoom, zoomx, zoomy);

        canvas.drawCircle(centerx, centery, earthRadius, earthPaint);

        // Draw the asteroids

        for (Asteroid a : asteroids.getAsteroids()) {

            // Determine the x and y coordinates of the asteroid

            float x = (float) (centerx + (a.getDistanceFromEarth() * Math.sin(a.getAngleFromEarth())));
            float y = (float) (centery - (a.getDistanceFromEarth() * Math.cos(a.getAngleFromEarth())));

            canvas.drawCircle(x, y, a.getRadius(), asteroidPaint);
        }

        canvas.restore();

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
