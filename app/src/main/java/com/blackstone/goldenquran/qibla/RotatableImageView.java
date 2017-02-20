
package com.blackstone.goldenquran.qibla;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.blackstone.goldenquran.R;


public class RotatableImageView extends ImageView implements Rotatable {
    private static final String TAG = "CompassView";
    private int extraPadding;
    private float mRotateDegree;

    public RotatableImageView(Context context) {
        super(context);
    }

    public RotatableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.RotatableImageView,
                0, 0);

        try {
            extraPadding = a.getInteger(R.styleable.RotatableImageView_extraPadding, 0);
        } finally {
            a.recycle();
        }
    }

    public RotatableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RotatableImageView,
                0, 0);

        try {
            extraPadding = a.getInteger(R.styleable.RotatableImageView_extraPadding, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(mRotateDegree, getWidth() / 2+extraPadding, getHeight() / 2+extraPadding);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    public void rotate(double degree) {
        mRotateDegree = (float) degree;
        invalidate();
    }
}
