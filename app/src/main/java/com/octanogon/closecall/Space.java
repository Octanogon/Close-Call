package com.octanogon.closecall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Space extends View {

    private Paint earthPaint;

    private int currentWidth;
    private int currentHeight;

    private int earthRadius = 100;


    public Space(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

    }

    private void init() {
        earthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        earthPaint.setStyle(Paint.Style.FILL);
        earthPaint.setColor(Color.GREEN);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(currentWidth/2,currentHeight/2,earthRadius, earthPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        currentHeight = h;
        currentWidth = w;



    }
}
