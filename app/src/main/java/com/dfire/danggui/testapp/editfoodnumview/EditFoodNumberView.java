package com.dfire.danggui.testapp.editfoodnumview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;
import com.dfire.danggui.testapp.utils.FeeHelper;
import com.dfire.danggui.testapp.utils.NumberUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Description：编辑购物车商品数量控件
 * <br/>
 * Created by kumu on 2017/3/30.
 */

public class EditFoodNumberView extends LinearLayout {

    public static final int CLICK_LEFT = 1;
    public static final int CLICK_RIGHT = 2;
    public static final int CLICK_MIDDLE = 3;

    private static final int TEXT_MARGIN_DP = 10;

    private double mCount;
    private int mUnitStringRes;

    private AppCompatEditText mEditCount;
    private TextView mTextUnit;

    private OnEditViewClick mOnEditViewClick;
    private SoftBackListener mSoftBackListener;
    private OnEditTextChangeListener mOnEditTextChangeListener;
    private OnInputDone mOnInputDone;
    private Context mContext;

    public EditFoodNumberView(Context context) {
        super(context);
        init(context, null);
    }

    public EditFoodNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditFoodNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnEditViewClick(OnEditViewClick onEditViewClick) {
        this.mOnEditViewClick = onEditViewClick;
    }

    public void setOnInputDone(OnInputDone onInputDone) {
        this.mOnInputDone = onInputDone;
    }

    public void setOnEditTextChangeListener(OnEditTextChangeListener onEditTextChangeListener) {
        mOnEditTextChangeListener = onEditTextChangeListener;
    }

    public void setSoftBackListener(SoftBackListener softBackListener) {
        mSoftBackListener = softBackListener;
    }

    public void plus() {
        if (mMaxValue != -1 && mCount + 1 > mMaxValue) {
            return;
        }
        setEditText(mEditCount, String.valueOf(++mCount));
        mEditCount.setSelection(mEditCount.getText().length());
    }

    public void minus() {
        if (mMinValue != -1 && mCount - 1 < mMinValue) {
            return;
        }
        setEditText(mEditCount, String.valueOf(--mCount));
        mEditCount.setSelection(mEditCount.getText().length());
    }

    public void setNumberText(double number) {
        if (number <= mMaxValue && number >= mMinValue) {
            mCount = number;
        } else if (number > mMaxValue) {
            mCount = mMaxValue;
        } else if (number < mMinValue) {
            mCount = mMinValue;
        }
        setEditText(mEditCount, FeeHelper.getDecimalFee(mCount));
        mEditCount.setSelection(mEditCount.getText().length());
    }


    public void setUnitText(@StringRes int resId) {
        if (mTextUnit == null) {
            mTextUnit = createUnitText();
        }
        mTextUnit.setText(resId);
    }

    public void setUnitText(String unit) {
        if (mTextUnit == null) {
            mTextUnit = createUnitText();
        }
        mTextUnit.setText(unit);
    }

    private TextView createUnitText() {
        TextView textView = new TextView(getContext());
        textView.setText(mUnitStringRes);
        if (mMiddleTextSize != -1)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMiddleTextSize);
        if (mMiddleTextColor != -1)
            textView.setTextColor(mMiddleTextColor);

        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        return textView;
    }


    //ImageView  TextView ImageView

    private double mMaxValue = -1, mMinValue = -1;

    private int mMiddleTextColor = -1, mPrefixImageRes = -1,
            mLeftImageRes = -1, mRightImageRes = -1, mMiddleTextSize = -1;

    private int mMiddleHintColor = -1, mMiddleHintText = -1;

    private int mTextMargin = 10;

    private void init(final Context context, AttributeSet attrs) {
        mContext = context;
        mTextMargin = (int) (TEXT_MARGIN_DP * context.getResources().getDisplayMetrics().density + 0.5f);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Module_Menu_EditFoodNumberView);

            mLeftImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_left_image, -1);
            mPrefixImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_prefix_image, -1);


            mMiddleHintColor = typedArray.getColor(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_hint_color, -1);
            mMiddleHintText = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_hint_text, -1);


            mMaxValue = typedArray.getFloat(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_max_value, -1);
            mMinValue = typedArray.getFloat(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_min_value, -1);
            mRightImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_right_image, -1);

            mMiddleTextColor = typedArray.getColor(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_text_color, -1);
            mMiddleTextSize = typedArray.getDimensionPixelSize(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_text_size, -1);

            mUnitStringRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_unit, -1);

            typedArray.recycle();
        }

        //Prefix Image
        if (mPrefixImageRes != -1) {
            ImageView prefixImage = new ImageView(getContext());
            prefixImage.setImageResource(mPrefixImageRes);
            LayoutParams prefixLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            prefixLp.setMargins(0, 0, 5, 0);
            prefixImage.setLayoutParams(prefixLp);
            addView(prefixImage);
            prefixImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnEditViewClick != null) {
                        mOnEditViewClick.onClick(CLICK_LEFT, mCount);
                    }
                }
            });
        }

        if (mLeftImageRes != -1) {
            ImageView leftImage = new ImageView(getContext());
            LayoutParams imageLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            leftImage.setLayoutParams(imageLp);
            leftImage.setImageResource(mLeftImageRes);
            addView(leftImage);
            RxView.clicks(leftImage).throttleFirst(100, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (mOnEditViewClick != null) {
                                hideSoft(mEditCount);
                                mOnEditViewClick.onClick(CLICK_LEFT, mCount);
                            }
                        }
                    });
        }
        //数量
        mEditCount = new AppCompatEditText(getContext());
        mEditCount.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_BACK)) {
                    if (mSoftBackListener != null) {
                        mSoftBackListener.onSoftBack(mEditCount, getNumber());
                    }
                    //return true;
                }
                return false;
            }
        });
        if (mMiddleHintColor != -1) {
            mEditCount.setHintTextColor(mMiddleHintColor);
        }

        Selection.setSelection(mEditCount.getText(), mEditCount.getText().length());
        mEditCount.setBackgroundResource(0);
        if (mMiddleTextSize != -1)
            mEditCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMiddleTextSize);
        if (mMiddleTextColor != -1)
            mEditCount.setTextColor(mMiddleTextColor);
        mEditCount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mEditCount.setMaxLines(1);
        mEditCount.setSingleLine(true);
        if (mMiddleHintText != -1) {
            mEditCount.setHint(mMiddleHintText);
        } else {
            setEditText(mEditCount, String.valueOf(mCount));
        }
        mEditCount.setSelection(mEditCount.getText().length());
        mEditCount.setLongClickable(false);
        mEditCount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditCount.addTextChangedListener(new TextWatcher() {
            boolean deleteLastChar;// 是否需要删除末尾

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    // 如果点后面有超过三位数值,则删掉最后一位
                    int length = s.length() - s.toString().lastIndexOf(".");
                    // 说明后面有三位数值
                    deleteLastChar = length >= 4;
                } else {
                    deleteLastChar = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                handleEditTextChange(s, deleteLastChar);
            }
        });
        mEditCount.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText1 = (EditText) v;
                // 以小数点结尾，去掉小数点
                if (!hasFocus && editText1.getText() != null && editText1.getText().toString().endsWith(".")) {
                    setEditText(mEditCount, String.valueOf(editText1.getText().subSequence(0, editText1.getText().length() - 1)));
                    mEditCount.setSelection(mEditCount.getText().length());
                }
            }
        });
        //监听键盘点击“完成/Done”代表输入完成
        mEditCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    String editStr = mEditCount.getText().toString();
                    //一开始是0，输入1，就变成了01，所以要重新设置EditText的值为1
                    if (editStr.length() > 1 && editStr.startsWith("0")) {
                        setEditText(mEditCount, editStr.substring(1));
                        mEditCount.setSelection(mEditCount.length());
                    }
                    if (editStr.length() >= 1 && editStr.startsWith(".")) {
                        setEditText(mEditCount, "0" + editStr);
                        mEditCount.setSelection(mEditCount.length());
                    }
                    if (editStr.length() > 1 && editStr.endsWith(".")) {
                        setEditText(mEditCount, editStr.substring(0, editStr.length() - 1));
                        mEditCount.setSelection(mEditCount.length());
                    }
                    mEditCount.clearFocus();
                    hideSoft(mEditCount);
                    if (mOnInputDone != null) {
                        mOnInputDone.onDone(mCount);
                    }
                }
                return false;
            }
        });
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        mEditCount.setLayoutParams(lp);
        addView(mEditCount);

        //单位
        if (mUnitStringRes != -1) {
            mTextUnit = createUnitText();
            addView(mTextUnit);

            mTextUnit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditCount.requestFocus();
                    mEditCount.setSelection(mEditCount.length());
                    if (mOnEditViewClick != null) {
                        mOnEditViewClick.onClick(CLICK_MIDDLE, mCount);
                    }
                    InputMethodManager inputManager =
                            (InputMethodManager) mEditCount.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(mEditCount, 0);
                }
            });
        }

        if (mRightImageRes != -1) {
            ImageView rightImage = new ImageView(getContext());
            rightImage.setImageResource(mRightImageRes);
            LayoutParams rightLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rightImage.setLayoutParams(rightLp);
            addView(rightImage);
            RxView.clicks(rightImage).throttleFirst(100, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (mOnEditViewClick != null) {
                                hideSoft(mEditCount);
                                mOnEditViewClick.onClick(CLICK_RIGHT, mCount);
                            }
                        }
                    });
        }

        mEditCount.setPadding(
                mLeftImageRes != -1 ? mTextMargin : 0,
                0,
                mRightImageRes != -1 && mUnitStringRes == -1 ? mTextMargin : 0,
                0);

        if (mRightImageRes != -1 && mTextUnit != null) {
            mTextUnit.setPadding(0, 0, mTextMargin, 0);
        }
    }

    public void setInputType(int inputType) {
        mEditCount.setInputType(inputType);
    }

    public boolean hasInput() {
        if (mEditCount == null) {
            return false;
        }
        return mEditCount.getText().toString().trim().length() != 0;
    }

    public EditText getEditText() {
        return mEditCount;
    }

    public double getNumber() {
        return mCount;
    }

    /**
     * 处理输入框内容变化
     *
     * @param s
     */
    private void handleEditTextChange(Editable s, boolean deleteLastChar) {
        if (!TextUtils.isEmpty(s)) {
            String editStr = s.toString();
            if (deleteLastChar) {
                // 设置新的截取的字符串
                setEditText(mEditCount, editStr.substring(0, editStr.length() - 1), true);
                // 光标强制到末尾
                mEditCount.setSelection(mEditCount.getText().length());
            }
            // 以小数点开头，前面自动加上 "0"
            if (editStr.startsWith(".")) {
                setEditText(mEditCount, "0" + s);
                mEditCount.setSelection(mEditCount.getText().length());
            }
            double tmpCount;
            try {
                tmpCount = Double.parseDouble(mEditCount.getText().toString());
                if (mMaxValue != -1 && tmpCount > mMaxValue) {
                    if (TextUtils.isDigitsOnly(s)) {
                        setEditText(mEditCount, String.valueOf((int) mCount));
                    } else {
                        setEditText(mEditCount, String.valueOf(mCount));
                    }
                    mEditCount.setSelection(mEditCount.length());
                } else {
                    //如果是0打头，截取掉首字符0
                    if (editStr.length() > 1 && editStr.startsWith("0") && editStr.charAt(1) != '.') {
                        setEditText(mEditCount, editStr.substring(1));
                        mEditCount.setSelection(mEditCount.length());
                    } else {
                        mCount = tmpCount;
                        performOnEditTextChange(mCount);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            setEditText(mEditCount, "0");
            mEditCount.setSelection(mEditCount.length());
            mCount = 0;
            performOnEditTextChange(mCount);
        }
    }

    private void performOnEditTextChange(double number) {
        if (null != mOnEditTextChangeListener) {
            mOnEditTextChangeListener.OnEditTextChange(number);
        }
    }

    private void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    private void setEditText(EditText editText, String text) {
        setEditText(editText, text, false);
    }

    /**
     * 修改输入框内容
     *
     * @param editText
     * @param text
     * @param isByInput 是否是通过手动输入
     */
    private void setEditText(EditText editText, String text, boolean isByInput) {
        try {
            double doubleValue = Double.parseDouble(text);
            //如果内容是整数并且不是通过手输，转成整型值
            if (!isByInput && NumberUtils.doubleIsInteger(doubleValue)) {
                editText.setText((int) doubleValue + "");
            } else {
                editText.setText(text);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            editText.setText(text);
        }
    }

    public void setMaxValue(double maxValue) {
        mMaxValue = maxValue;
    }

    public void setMinValue(double minValue) {
        mMinValue = minValue;
    }

    private void hideSoft(View view) {
        // TODO: 2017/5/12 4.4系统会内存泄露
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public interface SoftBackListener {
        void onSoftBack(TextView textView, double inputNum);
    }

    public interface OnEditViewClick {
        void onClick(int which, double numberValue);
    }

    public interface OnEditTextChangeListener {
        void OnEditTextChange(double numberValue);
    }

    public interface OnInputDone {
        void onDone(double numberValue);
    }

}