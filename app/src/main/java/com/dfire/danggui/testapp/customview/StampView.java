package com.dfire.danggui.testapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.dfire.danggui.testapp.R;

/**
 * @author DangGui
 * @create 2017/11/3.
 */

public class StampView extends View {
    private Paint mPaint;
    /**
     * view占据的宽度
     */
    private int mViewWidth;
    /**
     * 根据mOuterCircleWidthDpVal计算的外圆实际宽度（px）
     */
    private int mOuterCircleWidth;
    /**
     * 内圆实际宽度（px）
     */
    private int mInnerCircleWidth;
    /**
     * 绘图颜色值
     */
    private int mColorRes = -1;
    /**
     * 主文本
     */
    private String mContentText;
    /**
     * 次级文本
     */
    private String mSubContentText;
    /**
     * 主文本字体大小
     */
    private int mContentTextSize;
    /**
     * 次级文本字体大小
     */
    private int mSubContentTextSize;
    /**
     * View旋转角度，默认-30
     */
    private int mRotationDegree;
    /**
     * 双杠线起始点坐标集合
     */
    private float[] mLinesPoints;
    /**
     * 外圆画笔宽度
     */
    private int mOuterCircleStrokeWidth;
    /**
     * 内圆画笔宽度
     */
    private int mInnerCircleStrokeWidth;
    /**
     * 双杠线画笔宽度
     */
    private int mLineStrokeWidth;
    /**
     * 默认（最小）外圆宽度
     */
    private static final int DEFAULT_OUTER_CIRCLE_WIDTH = 60;
    /**
     * 默认外圆画笔宽度
     */
    private static final int DEFAULT_OUTER_CIRCLE_STROKE_WIDTH = 5;
    /**
     * 默认内圆画笔宽度
     */
    private static final int DEFAULT_INNERER_CIRCLE_STROKE_WIDTH = 3;
    /**
     * 值增量变化因子（根据外圆半径和默认半径的比值）
     */
    private static final float WIDTH_FACTOR = 0.5f;

    public StampView(Context context) {
        super(context);
        init(context, null);
    }

    public StampView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StampView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mViewWidth = dp2px(DEFAULT_OUTER_CIRCLE_WIDTH);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mViewWidth = dp2px(DEFAULT_OUTER_CIRCLE_WIDTH);
            mViewWidth = mViewWidth > heightSize ? heightSize : mViewWidth;
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int mViewHeight = dp2px(DEFAULT_OUTER_CIRCLE_WIDTH);
            mViewWidth = widthSize > mViewHeight ? mViewHeight : widthSize;
        } else {
            mViewWidth = widthSize > heightSize ? heightSize : widthSize;
        }
        setMeasuredDimension(mViewWidth, mViewWidth);
        measureView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将画布移到中央
        //绘制外圆
        canvas.translate(mViewWidth / 2, mViewWidth / 2);
        canvas.rotate(mRotationDegree);
        mPaint.setStrokeWidth(mOuterCircleStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, mOuterCircleWidth / 2, mPaint);
        //绘制内圆
        mPaint.setStrokeWidth(mInnerCircleStrokeWidth);
        canvas.drawCircle(0, 0, mInnerCircleWidth / 2, mPaint);
        //绘制圆内上下双杠线
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mLineStrokeWidth);
        canvas.drawLines(mLinesPoints, mPaint);
        //绘制文字
        mPaint.setTextSize(mContentTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(mContentText, 0, 0, mPaint);
        if (!TextUtils.isEmpty(mSubContentText)) {
            mPaint.setTextSize(mSubContentTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(Typeface.DEFAULT);
            canvas.drawText(mSubContentText, 0, mSubContentTextSize + dp2px(2), mPaint);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StampView);
            mColorRes = ta.getColor(R.styleable.StampView_sv_color, -1);
            mContentText = ta.getString(R.styleable.StampView_sv_content_text);
            mSubContentText = ta.getString(R.styleable.StampView_sv_sub_text);
            ta.recycle();
        }
        if (mColorRes <= 0) {
            mColorRes = R.color.stamp_color;
        }
        if (TextUtils.isEmpty(mContentText)) {
            mContentText = "";
        }
        if (TextUtils.isEmpty(mSubContentText)) {
            mSubContentText = "";
        }
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(mColorRes));
    }

    private void measureView() {
        int defaultOuterCircleWidth = dp2px(DEFAULT_OUTER_CIRCLE_WIDTH);
        int scale = (int) ((mViewWidth / defaultOuterCircleWidth) * WIDTH_FACTOR);
        if (mViewWidth <= defaultOuterCircleWidth) {
            scale = 0;
        }
        mOuterCircleStrokeWidth = DEFAULT_OUTER_CIRCLE_STROKE_WIDTH + scale;
        mInnerCircleStrokeWidth = DEFAULT_INNERER_CIRCLE_STROKE_WIDTH + scale;
        mLineStrokeWidth = 2 + scale;
        int tempOuterCircleWidth = mViewWidth - mOuterCircleStrokeWidth * 2;
        if (mLineStrokeWidth > DEFAULT_INNERER_CIRCLE_STROKE_WIDTH) {
            mLineStrokeWidth = DEFAULT_INNERER_CIRCLE_STROKE_WIDTH;
        }
        mOuterCircleWidth = tempOuterCircleWidth;
        mInnerCircleWidth = mOuterCircleWidth - dp2px((5 + scale));
        int contentLength = 3;
        if (mContentText.length() > 4) {
            mContentText = mContentText.substring(0, 3);
        }
        if (mSubContentText.length() > 5) {
            mSubContentText = mSubContentText.substring(0, 4);
        }
        mContentTextSize = (mInnerCircleWidth - dp2px((7 + scale) * 2)) / contentLength;
        mSubContentTextSize = (int) (mContentTextSize * 0.8);
        mRotationDegree = -30;
        //计算双杠线坐标（一共4条线）
        //内圆半径
        int radius = mInnerCircleWidth / 2;
        //双杠线长度
        int lineRadius = radius / 2;
        //双杠线直接的间隔
        int linesDimen = dp2px(2);
        //短线短于长线的差值
        int shortLinePadding = dp2px(4);
        float line1dx = -lineRadius;
        float line1dy = -radius * 0.7f;
        float line2dx = -lineRadius + shortLinePadding;
        float line2dy = line1dy - linesDimen;
        float line3dx = line1dx;
        float line3dy = radius * 0.7f;
        float line4dx = line2dx;
        float line4dy = line3dy + linesDimen;
        mLinesPoints = new float[]{line1dx, line1dy, line1dx + radius, line1dy,
                line2dx, line2dy, line2dx + radius - shortLinePadding * 2, line2dy,
                line3dx, line3dy, line3dx + radius, line3dy,
                line4dx, line4dy, line4dx + radius - shortLinePadding * 2, line4dy};
    }

    /**
     * 将 dp 转换为 px
     *
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.mContentText = "";
        } else {
            this.mContentText = text;
        }
        invalidate();
    }

    public void setSubText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.mSubContentText = "";
        } else {
            this.mSubContentText = text;
        }
        invalidate();
    }

    public void setText(@StringRes int textRes) {
        if (textRes > 0) {
            this.mContentText = getResources().getString(textRes);
            invalidate();
        }
    }

    public void setSubText(@StringRes int textRes) {
        if (textRes > 0) {
            this.mSubContentText = getResources().getString(textRes);
            invalidate();
        }
    }

    public void setColor(@ColorRes int colorRes) {
        if (colorRes > 0) {
            this.mColorRes = colorRes;
            invalidate();
        }
    }
}
