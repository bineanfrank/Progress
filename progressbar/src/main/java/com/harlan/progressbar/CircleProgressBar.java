package com.harlan.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by Harlan on 2016/5/18.
 */
public class CircleProgressBar extends LineProgressBar {

    private static final String TAG = CircleProgressBar.class.getSimpleName();

    private static final int CIRCLE_ANGLE = 360;
    private static final int DEFAULT_RADIUS = 30;//DP
    private int mRadius = dp2px(DEFAULT_RADIUS);
    private int mMaxPaintWidth;
    private Paint mPaint;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //ReachHeight needs to be wider than unreachHeight
        mReachHeight = (int) (mUnreachHeight * 1.5f);

        //fetch attrs
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mRadius = (int) mTypedArray.getDimension(R.styleable.CircleProgressBar_radius, mRadius);
        mTypedArray.recycle();

        //init paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMaxPaintWidth = Math.max(mReachHeight, mUnreachHeight);

        //expect width/height if not given the exact value
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingRight() + getPaddingLeft();

        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        int realWidth = Math.min(width, height);
        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(realWidth, realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = mPaint.descent() + mPaint.ascent();

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);

        //draw unreached bar
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //draw reached bar
        float sweepAngle = getProgress() * 1.0f / getMax() * CIRCLE_ANGLE;
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweepAngle, false, mPaint);

        //draw text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mTextSize);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight / 2, mPaint);

        canvas.restore();
    }
}
