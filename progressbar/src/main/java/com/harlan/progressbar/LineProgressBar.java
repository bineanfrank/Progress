package com.harlan.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by Harlan on 2016/5/18.
 */
public class LineProgressBar extends ProgressBar {

    private static final String TAG = LineProgressBar.class.getSimpleName();

    //Default custom attributes.
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_UNREACH_COLOR = 0xFFD3D6DA;
    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT = 2;//DP
    private static final int DEFAULT_UNREACH_HEIGHT = 2;//DP
    private static final int DEFAULT_TEXT_OFFSET = 10;//DP
    private static final int DEFAULT_TEXT_SIZE = 10;//SP

    //Custom attributes.
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    //A CircleProgressBar also needs these attrs.
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mUnreachColor = DEFAULT_UNREACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);

    //Paint to draw.
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //Real width of progressbar, including padding values.
    private int mRealWidth;

    public LineProgressBar(Context context) {
        this(context, null);
    }

    public LineProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);

        //textSize need to be settled before measure the whole height of progressbar
        mPaint.setTextSize(mTextSize);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * Get the customized attributes values.
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mTextColor = mTypedArray.getColor(R.styleable.ProgressBar_textColor, mTextColor);
        mTextSize = (int) mTypedArray.getDimension(R.styleable.ProgressBar_textSize, mTextSize);
        mTextOffset = (int) mTypedArray.getDimension(R.styleable.ProgressBar_textOffset, mTextOffset);
        mReachColor = mTypedArray.getColor(R.styleable.ProgressBar_reachColor, mReachColor);
        mUnreachColor = mTypedArray.getColor(R.styleable.ProgressBar_unreachColor, mUnreachColor);
        mReachHeight = (int) mTypedArray.getDimension(R.styleable.ProgressBar_reachHeight, mReachHeight);
        mUnreachHeight = (int) mTypedArray.getDimension(R.styleable.ProgressBar_unreachHeight, mUnreachHeight);
        mTypedArray.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //width value do not need to be measured, since it is meaningless.
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        //the real width of progressbar
        mRealWidth = getPaddingLeft() + getPaddingRight() + width;
    }

    /**
     * Measure the height of progressbar
     * the height of text and the height of unreached and reached bar need be considered.
     *
     * @param heightMeasureSpec
     * @return the height of progressbar.
     */
    private int measureHeight(int heightMeasureSpec) {
        int resultHeight;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            resultHeight = size;
        } else {
            //the final height = paddingTop + paddingBottom + max(reachBarHeight, unreachBarHeight, textHeight)
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            resultHeight = getPaddingBottom() + getPaddingTop() + Math.max(
                    Math.max(textHeight, mReachHeight),
                    mUnreachHeight);
        }

        return resultHeight;
    }

    /**
     * 1. draw reached bar
     * 2. draw text
     * 3. draw unreached bar
     *
     * @param canvas
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();

        //translate to the vertical center of left start point
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        //when the whole width of text and reached bar >= the width of progressbar
        //the unreached part needn't to be drew.
        boolean noNeedUnreach = false;

        //draw reach bar
        String text = getProgress() + "%";
        float radio = getProgress() * 1.0f / getMax();
        float mReachWidth = radio * mRealWidth;
        int textWidth = (int) mPaint.measureText(text);
        if (textWidth + mReachWidth > mRealWidth) {
            noNeedUnreach = true;

            //when unreached part do not need to be drew, mReachWidth = mRealWidth - textWidth
            mReachWidth = mRealWidth - textWidth;
        }

        float endX = mReachWidth - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        canvas.drawText(text, mReachWidth, y, mPaint);

        //draw unreach bar
        if (noNeedUnreach) return;
        float start = mReachWidth + mTextOffset / 2 + textWidth;
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawLine(start, 0, mRealWidth, 0, mPaint);

        canvas.restore();
    }

    /**
     * Convert dp value to px value.
     *
     * @param value
     * @return
     */
    protected int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    /**
     * Convert sp value to px value.
     *
     * @param value
     * @return
     */
    protected int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
