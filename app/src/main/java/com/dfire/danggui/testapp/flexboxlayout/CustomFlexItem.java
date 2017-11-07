package com.dfire.danggui.testapp.flexboxlayout;

/**
 * @author DangGui
 * @create 2017/4/18.
 */

public class CustomFlexItem {
    /**
     * 唯一标示，代表该item的id，可以是菜肴id等
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否选中
     */
    private boolean checked;

    public CustomFlexItem(String id, String name) {
        this(id, name, false);
    }

    public CustomFlexItem(String id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
