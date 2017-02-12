package com.blackstone.goldenquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

public class DrawView extends ImageView {

    public ArrayList<Float> top, bottom, left, right;
    public boolean touched = false;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (touched) {
            Paint paint = new Paint();
            paint.setColor(new Color().argb(75, 220, 0, 0));
            paint.setStyle(Paint.Style.FILL);

            for (int i = 0; i < left.size(); i++) {
                canvas.drawRect(left.get(i), top.get(i), right.get(i), bottom.get(i), paint);
            }
        }
    }
}