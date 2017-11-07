package com.dfire.danggui.testapp.stickyrecyclerview.bean;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2016/12/17.
 */

public class Desk implements Serializable {
    private String deskPicUrl;
    private String deskName;
    private int deskId;
    private String scope;//属于哪个区域 比如“一楼”
    private int scopeId;//区域id，属于哪个区域 比如“一楼”

    public String getDeskPicUrl() {
        return deskPicUrl;
    }

    public void setDeskPicUrl(String deskPicUrl) {
        this.deskPicUrl = deskPicUrl;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }
}
