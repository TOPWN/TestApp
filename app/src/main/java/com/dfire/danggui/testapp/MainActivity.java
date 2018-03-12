package com.dfire.danggui.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dfire.danggui.testapp.annotation.TestAnnotationActivity;
import com.dfire.danggui.testapp.clipchildren.TestClipChildrenActivity;
import com.dfire.danggui.testapp.couponview.TestCouponViewActivity;
import com.dfire.danggui.testapp.customview.StampViewActivity;
import com.dfire.danggui.testapp.editfoodnumview.TestEditFoodNumViewActivity;
import com.dfire.danggui.testapp.eventbus.TestEventBusSubActivity;
import com.dfire.danggui.testapp.flexboxlayout.TestFlexboxLayoutActivity;
import com.dfire.danggui.testapp.glide.TestGlideActivity;
import com.dfire.danggui.testapp.softkeyboard.TestSoftKeyboardActivity;
import com.dfire.danggui.testapp.stateview.InjectActivity;
import com.dfire.danggui.testapp.stickyrecyclerview.StickyRecyclerActivity;
import com.example.Test;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Test
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btStickyRecycler)
    Button btStickyRecycler;
    @Bind(R.id.btCouponView)
    Button btCouponView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.button_clipchildren)
    Button mButtonClipchildren;
    @Bind(R.id.button_test_stateview)
    Button mButtonTestStateview;
    @Bind(R.id.button_test_glide)
    Button mButtonTestGlide;
    @Bind(R.id.button_flexboxlayout)
    Button mButtonTestFlexboxlayout;
    @Bind(R.id.button_TestAnnotationActivity)
    Button mButtonTestAnnotationActivity;
    @Bind(R.id.button_TestSoftKeybordActivity)
    Button mButtonTestSoftKeybordActivity;
    @Bind(R.id.button_StampViewActivity)
    Button mButtonStampViewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btStickyRecycler, R.id.btCouponView, R.id.btTestEventBus, R.id.button_clipchildren
            , R.id.button_test_dfire_network, R.id.button_test_stateview, R.id.button_test_glide, R.id.button_flexboxlayout
            , R.id.button_EditFoodNumView, R.id.button_TestAnnotationActivity, R.id.button_TestSoftKeybordActivity
            , R.id.button_StampViewActivity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btStickyRecycler:
                Intent intent = new Intent(this, StickyRecyclerActivity.class);
                startActivity(intent);
                break;
            case R.id.btCouponView:
                Intent intent2 = new Intent(this, TestCouponViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.btTestEventBus:
                Intent intent3 = new Intent(this, TestEventBusSubActivity.class);
                startActivity(intent3);
                break;
            case R.id.button_clipchildren:
                Intent intent4 = new Intent(this, TestClipChildrenActivity.class);
                startActivity(intent4);
                break;
            case R.id.button_test_stateview:
                Intent intent6 = new Intent(this, InjectActivity.class);
                startActivity(intent6);
                break;
            case R.id.button_test_glide:
                Intent intent7 = new Intent(this, TestGlideActivity.class);
                startActivity(intent7);
                break;
            case R.id.button_flexboxlayout:
                Intent intent8 = new Intent(this, TestFlexboxLayoutActivity.class);
                startActivity(intent8);
                break;
            case R.id.button_EditFoodNumView:
                Intent intent9 = new Intent(this, TestEditFoodNumViewActivity.class);
                startActivity(intent9);
                break;
            case R.id.button_TestAnnotationActivity:
                Intent intent10 = new Intent(this, TestAnnotationActivity.class);
                startActivity(intent10);
                break;
            case R.id.button_TestSoftKeybordActivity:
                Intent intent11 = new Intent(this, TestSoftKeyboardActivity.class);
                startActivity(intent11);
                break;
            case R.id.button_StampViewActivity:
                Intent intent12 = new Intent(this, StampViewActivity.class);
                startActivity(intent12);
                break;
        }
    }
}
