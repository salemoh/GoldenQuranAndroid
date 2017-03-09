package com.blackstone.goldenquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

public class DrawView extends android.support.v7.widget.AppCompatImageView {

    public ArrayList<Float> top, bottom, left, right;
    private static final int red = new Color().argb(75, 220, 0, 0);
    Paint paint = new Paint();
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
            paint.setColor(red);
            paint.setStyle(Paint.Style.FILL);

            for (int i = 0; i < left.size(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawRoundRect(left.get(i), top.get(i), right.get(i), bottom.get(i), 20, 20, paint);
                } else {
                    canvas.drawRect(left.get(i), top.get(i), right.get(i), bottom.get(i), paint);
                }
            }
        }
    }
}