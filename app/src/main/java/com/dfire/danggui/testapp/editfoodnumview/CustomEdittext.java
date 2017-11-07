package com.dfire.danggui.testapp.editfoodnumview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author DangGui
 * @create 2017/7/10.
 */

public class CustomEdittext extends android.support.v7.widget.AppCompatEditText {
    public CustomEdittext(Context context) {
        super(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
