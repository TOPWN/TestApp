package com.dfire.danggui.testapp.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;
import com.example.DIActivity;
import com.example.DIView;

/**
 * @author DangGui
 * @create 2017/7/22.
 */

@DIActivity
public class TestAnnotationActivity extends Activity {

    @DIView(R.id.text_checked)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_annotation);
        DITestAnnotationActivity.bindView(this);
        mTextView.setText("Test Annotation");
    }
}
