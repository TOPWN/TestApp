package com.dfire.danggui.testapp.stickyrecyclerview.bean;


import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DeskSection extends SectionEntity<Desk> {
    private int sectionId;//当前section的id

    public DeskSection(boolean isHeader, String header, int sectionId) {
        super(isHeader, header);
        this.sectionId = sectionId;
    }

    public DeskSection(Desk t) {
        super(t);
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
