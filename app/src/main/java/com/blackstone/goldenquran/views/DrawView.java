package com.blackstone.goldenquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import com.blackstone.goldenquran.models.Ayah;

import java.util.ArrayList;

public class DrawView extends android.support.v7.widget.AppCompatImageView {

    public ArrayList<Float> top, bottom, left, right;
    public ArrayList<Ayah> ayahs;
    public ArrayList<Integer> colors;
    private static final int red = new Color().argb(75, 220, 0, 0);
    private static final int green = new Color().argb(75, 0, 220, 0);
    private static final int black = new Color().argb(75, 0, 0, 0);
    private static final int blue = new Color().argb(75, 0, 0, 220);
    private static final int idk = new Color().argb(75, 44, 2, 220);
    private static final int omg = new Color().argb(75, 0, 222, 220);
    private static final int wtf = new Color().argb(75, 0, 856, 220);
    private static final int terararara = new Color().argb(75, 0, 0, 220);
    public int imageWidth, imageHeight;
    int ayahColor;

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

        if (!touched) {
            paint.setStyle(Paint.Style.FILL);
            if (ayahs != null && colors != null)
                for (int i = 0; i < colors.size(); i++) {
                    switch (colors.get(i)) {
                        case 1:
                            ayahColor = black;
                            break;
                        case 2:
                            ayahColor = idk;
                            break;
                        case 3:
                            ayahColor = omg;
                            break;
                        case 4:
                            ayahColor = green;
                            break;
                        case 5:
                            ayahColor = blue;
                            break;
                        case 6:
                            ayahColor = wtf;
                            break;
                        case 7:
                            ayahColor = terararara;
                            break;
                    }
                    paint.setColor(ayahColor);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        canvas.drawRoundRect(
                                map(ayahs.get(i).upperLeftX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).upperLeftY, 0, imageHeight, 0, getHeight()),
                                map(ayahs.get(i).upperRightX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).lowerLeftY, 0, imageHeight, 0, getHeight()),
                                20,
                                20,
                                paint
                        );
                    } else {
                        canvas.drawRect(
                                map(ayahs.get(i).upperLeftX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).upperLeftY, 0, imageHeight, 0, getHeight()),
                                map(ayahs.get(i).upperRightX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).lowerLeftY, 0, imageHeight, 0, getHeight()),
                                paint);
                    }
                }
        } else {
            paint.setColor(red);
            paint.setStyle(Paint.Style.FILL);

            for (int i = 0; i < left.size(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawRoundRect(
                            left.get(i),
                            top.get(i),
                            right.get(i),
                            bottom.get(i),
                            20,
                            20,
                            paint
                    );
                } else {
                    canvas.drawRect(
                            left.get(i),
                            top.get(i),
                            right.get(i),
                            bottom.get(i),
                            paint);
                }
            }
            if (ayahs != null && colors != null)
                for (int i = 0; i < colors.size(); i++) {
                    switch (colors.get(i)) {
                        case 1:
                            ayahColor = black;
                            break;
                        case 2:
                            ayahColor = idk;
                            break;
                        case 3:
                            ayahColor = omg;
                            break;
                        case 4:
                            ayahColor = green;
                            break;
                        case 5:
                            ayahColor = blue;
                            break;
                        case 6:
                            ayahColor = wtf;
                            break;
                        case 7:
                            ayahColor = terararara;
                            break;
                    }
                    paint.setColor(ayahColor);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        canvas.drawRoundRect(
                                map(ayahs.get(i).upperLeftX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).upperLeftY, 0, imageHeight, 0, getHeight()),
                                map(ayahs.get(i).upperRightX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).lowerLeftY, 0, imageHeight, 0, getHeight()),
                                20,
                                20,
                                paint
                        );
                    } else {
                        canvas.drawRect(
                                map(ayahs.get(i).upperLeftX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).upperLeftY, 0, imageHeight, 0, getHeight()),
                                map(ayahs.get(i).upperRightX, 0, imageWidth, 0, getWidth()),
                                map(ayahs.get(i).lowerLeftY, 0, imageHeight, 0, getHeight()),
                                paint);
                    }
                }

        }
    }

    public float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}