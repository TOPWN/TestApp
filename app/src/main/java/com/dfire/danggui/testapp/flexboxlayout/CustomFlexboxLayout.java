package com.dfire.danggui.testapp.flexboxlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义FlexboxLayout，支持单选、多选
 *
 * @author DangGui
 * @create 2017/4/18.
 */

public class CustomFlexboxLayout extends FlexboxLayout {
    private Context mContext;
    /**
     * 数据源
     */
    private List<CustomFlexItem> mCustomFlexItemList;
    /**
     * 是否是单项选择
     */
    private boolean isSingleChoice = false;

    public CustomFlexboxLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void initSource(List<CustomFlexItem> customFlexItemList, boolean isSingleChoice) {
        this.mCustomFlexItemList = customFlexItemList;
        this.isSingleChoice = isSingleChoice;
        initFlexboxViewBySource();
    }

    /**
     * 根据数据源绘制FlexBoxLayout界面
     */
    private void initFlexboxViewBySource() {
        filterSource(mCustomFlexItemList, isSingleChoice);
        if (null != mCustomFlexItemList) {
            removeAllViews();
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_custom_flexboxlayout, null);
                TextView itemTextView = (TextView) itemView.findViewById(R.id.text_item);
                CustomFlexItem flexItem = mCustomFlexItemList.get(i);
                itemView.setTag(flexItem.getId());
                itemTextView.setText(flexItem.getName());
                checkSelectView(itemView, flexItem.isChecked());
                itemView.setOnClickListener(new ItemClickListener());
                addView(itemView);
            }

        }
    }

    /**
     * 过滤数据源，如果是单项选择，但数据源却有不止1项被默认选中，则重置所有项目未选中
     *
     * @param customFlexItemList
     * @param isSingleChoice
     */
    private void filterSource(List<CustomFlexItem> customFlexItemList, boolean isSingleChoice) {
        if (null == mCustomFlexItemList || mCustomFlexItemList.isEmpty() || !isSingleChoice) {
            return;
        }
        int checkedCount = 0;
        for (int i = 0; i < mCustomFlexItemList.size(); i++) {
            if (mCustomFlexItemList.get(i).isChecked()) {
                checkedCount++;
            }
        }
        if (checkedCount > 1) {
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                mCustomFlexItemList.get(i).setChecked(false);
            }
        }
    }

    /**
     * 获取被选中的item
     *
     * @return
     */
    public List<CustomFlexItem> getCheckedItemList() {
        List<CustomFlexItem> checkedItemList = new ArrayList<>();
        if (null == mCustomFlexItemList || mCustomFlexItemList.isEmpty()) {
            return checkedItemList;
        }
        for (int i = 0; i < mCustomFlexItemList.size(); i++) {
            CustomFlexItem customFlexItem = mCustomFlexItemList.get(i);
            if (customFlexItem.isChecked()) {
                checkedItemList.add(customFlexItem);
            }
        }
        return checkedItemList;
    }

    /**
     * 根据选中的view的tag来进行过滤
     *
     * @param id
     */
    private void checkSelected(String id) {
        if (getChildCount() >= mCustomFlexItemList.size()) {
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                View itemView = getChildAt(i);
                CustomFlexItem customFlexItem = mCustomFlexItemList.get(i);
                if (null != itemView.getTag()) {
                    boolean isValid = itemView.getTag().toString().equals(id);
                    if (isSingleChoice) {
                        checkSelectView(itemView, isValid);
                        customFlexItem.setChecked(isValid);
                    } else {
                        if (isValid) {
                            checkSelectView(itemView, !customFlexItem.isChecked());
                            customFlexItem.setChecked(!customFlexItem.isChecked());
                        }
                    }
                }
            }
        }
    }

    /**
     * 对选中/未选中的item设置背景
     *
     * @param itemView
     * @param checked
     */
    private void checkSelectView(View itemView, boolean checked) {
        if (itemView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) itemView;
            if (viewGroup.getChildCount() > 0) {
                if (checked) {
                    viewGroup.getChildAt(0).setBackgroundResource(R.drawable.shape_flexitem_checked_layers);
                } else {
                    viewGroup.getChildAt(0).setBackgroundResource(R.drawable.shape_flexitem_normal);
                }
            }
        }
    }

    private class ItemClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (null != v.getTag()) {
                checkSelected(v.getTag().toString());
            }
        }
    }
}
