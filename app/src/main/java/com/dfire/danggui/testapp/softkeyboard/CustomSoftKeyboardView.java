package com.dfire.danggui.testapp.softkeyboard;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dfire.danggui.testapp.R;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * 自定义数字键盘
 *
 * @author DangGui
 * @create 2017/7/29.
 */
public class CustomSoftKeyboardView extends RelativeLayout {
    private Context mContext;
    /**
     * 用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
     */
    private RecyclerView mRecyclerView;
    private SoftKeyboardAdapter mSoftKeyboardAdapter;
    private ArrayList<KeyboardModel> mValueList;
    private RelativeLayout mLayoutBack;
    private Animation mEnterAnim;
    private Animation mExitAnim;

    public CustomSoftKeyboardView(Context context) {
        this(context, null);
    }

    public CustomSoftKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = View.inflate(context, R.layout.layout_softkeyboard, null);
        mValueList = new ArrayList<>();
        mLayoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_keybord);
        mEnterAnim = AnimationUtils.loadAnimation(context, R.anim.softkeyboard_push_bottom_in);
        mExitAnim = AnimationUtils.loadAnimation(context, R.anim.softkeyboard_push_bottom_out);
        initValueList();
        addView(view);
    }

    public void init(Activity activity, final EditText editText) {
        if (null == editText) {
            return;
        }
        mSoftKeyboardAdapter.setOnItemClickListener(new KeyboardBaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof KeyboardModel) {
                    KeyboardModel keyboardModel = (KeyboardModel) data;
                    if (keyboardModel.getType() == KeyboardModel.ITEM_TYPE_NORMAL) {
                        if (!keyboardModel.getValue().equals(".")) {
                            String amount = editText.getText().toString().trim();
                            amount = amount + mValueList.get(position).getValue();
                            editText.setText(amount);
                            Editable ea = editText.getText();
                            editText.setSelection(ea.length());
                        } else {
                            String amount = editText.getText().toString().trim();
                            if (!amount.contains(".")) {
                                amount = amount + mValueList.get(position).getValue();
                                editText.setText(amount);
                                Editable ea = editText.getText();
                                editText.setSelection(ea.length());
                            }
                        }
                    } else if (keyboardModel.getType() == KeyboardModel.ITEM_TYPE_DELETE) {
                        String amount = editText.getText().toString().trim();
                        if (amount.length() > 0) {
                            amount = amount.substring(0, amount.length() - 1);
                            editText.setText(amount);
                            Editable ea = editText.getText();
                            editText.setSelection(ea.length());
                        }
                    } else if (keyboardModel.getType() == KeyboardModel.ITEM_TYPE_CONFIRM) {

                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
        // 设置不调用系统键盘
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(mExitAnim);
                setVisibility(View.GONE);
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusable(true);
                setFocusableInTouchMode(true);
                startAnimation(mEnterAnim);
                setVisibility(View.VISIBLE);
            }
        });
    }

    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 0; i < 14; i++) {
            KeyboardModel keyboardModel = new KeyboardModel(KeyboardModel.ITEM_TYPE_NORMAL, i);
            switch (i) {
                case 0:
                    keyboardModel.setValue("7");
                    break;
                case 1:
                    keyboardModel.setValue("8");
                    break;
                case 2:
                    keyboardModel.setValue("9");
                    break;
                case 3:
                    keyboardModel.setType(KeyboardModel.ITEM_TYPE_DELETE);
                    keyboardModel.setValue(mContext.getString(R.string.softkeyboard_delete));
                    break;
                case 4:
                    keyboardModel.setValue("4");
                    break;
                case 5:
                    keyboardModel.setValue("5");
                    break;
                case 6:
                    keyboardModel.setValue("6");
                    break;
                case 7:
                    keyboardModel.setValue("1");
                    break;
                case 8:
                    keyboardModel.setValue("2");
                    break;
                case 9:
                    keyboardModel.setValue("3");
                    break;
                case 10:
                    keyboardModel.setType(KeyboardModel.ITEM_TYPE_CONFIRM);
                    keyboardModel.setValue(mContext.getString(R.string.softkeyboard_confirm));
                    break;
                case 11:
                    keyboardModel.setValue(".");
                    break;
                case 12:
                    keyboardModel.setValue("0");
                    break;
                case 13:
                    keyboardModel.setValue("00");
                    break;
                default:
                    keyboardModel.setValue("0");
                    break;
            }
            mValueList.add(keyboardModel);
        }
        mSoftKeyboardAdapter = new SoftKeyboardAdapter(mContext, mRecyclerView, mValueList);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerView.setLayoutManager(new SpannedGridLayoutManager(
                new SpannedGridLayoutManager.GridSpanLookup() {
                    @Override
                    public SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
                        if (position == 3 || position == 10) {
                            return new SpannedGridLayoutManager.SpanInfo(1, 2);
                        } else {
                            return new SpannedGridLayoutManager.SpanInfo(1, 1);
                        }
                    }
                },
                4 /* 列数 */,
                1.5f /* Item 长宽比 */));
        mRecyclerView.addItemDecoration(new GridItemDividerDecoration(1, ContextCompat.getColor(mContext, R.color.softkeyboard_divider)));
        mRecyclerView.setAdapter(mSoftKeyboardAdapter);
    }
}
