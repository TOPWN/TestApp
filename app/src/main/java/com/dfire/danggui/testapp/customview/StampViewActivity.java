package com.dfire.danggui.testapp.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dfire.danggui.testapp.R;

/**
 * @author DangGui
 * @create 2017/11/3.
 */

public class StampViewActivity extends AppCompatActivity {
    StampView stampView;
    Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stampview);
         stampView= (StampView) findViewById(R.id.stamp);
         mButton= (Button) findViewById(R.id.button);
        stampView.setText("来劲了");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stampView.setText("年后");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
