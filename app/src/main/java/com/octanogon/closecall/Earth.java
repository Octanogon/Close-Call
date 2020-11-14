package com.octanogon.closecall;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

public class Earth {
    /* Represents Earth */

    private Bitmap earthBitmap;
    private Paint earthPaint;
    private Paint atmospherePaint;

    private int radius = 150;

    private ValueAnimator rotationAnimator;

    private final long ROTATION_DURATION = 10000;

    public Earth(Bitmap rawEarthBitmap)
    {
        init(rawEarthBitmap);
    }

    private void init(Bitmap rawEarthBitmap)
    {
        rotationAnimator = ValueAnimator.ofFloat(0, - (float) 360);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        rotationAnimator.setDuration(ROTATION_DURATION);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();

        earthBitmap = Bitmap.createScaledBitmap(rawEarthBitmap, radius*2, radius*2, true);

        earthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        earthPaint.setStyle(Paint.Style.FILL);

        atmospherePaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        atmospherePaint.setStyle(Paint.Style.FILL);


    }

    public void draw(Canvas canvas, int x, int y)
    {
        // Draws itself onto a given canvas at the given coordinates
        RadialGradient gradient = new RadialGradient(x, y, (int) (radius*1.5), Color.CYAN, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        atmospherePaint.setShader(gradient);

        canvas.save();
        canvas.rotate((float) rotationAnimator.getAnimatedValue(), x, y);
        canvas.drawCircle(x, y, (float) (radius*2), atmospherePaint);
        canvas.drawBitmap(earthBitmap, x-radius, y-radius, earthPaint);

        canvas.restore();

    }

}
