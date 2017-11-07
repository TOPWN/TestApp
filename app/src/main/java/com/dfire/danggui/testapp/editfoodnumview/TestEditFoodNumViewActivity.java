package com.dfire.danggui.testapp.editfoodnumview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.dfire.danggui.testapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/5/12.
 */

public class TestEditFoodNumViewActivity extends AppCompatActivity {
    @Bind(R.id.edit_first_food_number_view)
    EditFoodNumberView mEditFirstFoodNumberView;
    @Bind(R.id.edittext)
    EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_editfoodnumview_layout);
        ButterKnife.bind(this);
        mEditFirstFoodNumberView.setNumberText(66);
        mEditFirstFoodNumberView.setUnitText("份");
        mEditFirstFoodNumberView.getEditText().setText("0");
//        mEditText.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mEditText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
//            }
//        }, 2000);
        initListener();
    }

    private void initListener() {
        mEditFirstFoodNumberView.setOnEditViewClick(new EditFoodNumberView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case EditFoodNumberView.CLICK_LEFT:
                        subFoodNum(numberValue);
                        break;
                    case EditFoodNumberView.CLICK_RIGHT:
                        plusFoodNum(numberValue);
                        break;
                }
            }
        });
        mEditFirstFoodNumberView.setOnInputDone(new EditFoodNumberView.OnInputDone() {
            @Override
            public void onDone(double numberValue) {
                mEditFirstFoodNumberView.setNumberText(numberValue);
            }
        });
        mEditFirstFoodNumberView.setOnEditTextChangeListener(new EditFoodNumberView.OnEditTextChangeListener() {
            @Override
            public void OnEditTextChange(double numberValue) {
                if (numberValue < 0) {
                    numberValue = 0;
                }
                if (numberValue > 10000) {
                    numberValue = 10000;
                }
            }
        });
    }

    /**
     * 数量 减
     *
     * @param numberValue
     */
    private void subFoodNum(double numberValue) {
        double num = numberValue - 1;
        if (num < 0) {
            num = 0;
        }
        mEditFirstFoodNumberView.setNumberText(num);
    }

    /**
     * 数量 加
     *
     * @param numberValue
     */
    private void plusFoodNum(double numberValue) {
        double addNum = numberValue + 1;
        if (addNum > 10000) {
            addNum = 10000;
        }
        mEditFirstFoodNumberView.setNumberText(addNum);
    }
}
