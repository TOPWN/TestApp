package com.dfire.danggui.testapp.softkeyboard;

/**
 * @author DangGui
 * @create 2017/7/31.
 */

public class KeyboardModel {
    /**
     * 普通按钮
     */
    public static final int ITEM_TYPE_NORMAL = 1;
    /**
     * 确认按钮
     */
    public static final int ITEM_TYPE_CONFIRM = 2;
    /**
     * 删除按钮
     */
    public static final int ITEM_TYPE_DELETE = 3;

    /**
     * 类型（数字、删除、确认按钮）
     */
    private int type;
    /**
     * 键盘上的位置
     */
    private int position;
    /**
     * 数值
     */
    private String value;

    public KeyboardModel(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
