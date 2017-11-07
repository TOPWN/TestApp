package com.dfire.danggui.testapp.stickyrecyclerview;

import com.dfire.danggui.testapp.BasePresenter;
import com.dfire.danggui.testapp.BaseView;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.DeskSection;

import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public interface AttentionDeskContract {
    interface View extends BaseView<Presenter> {
        void showAttentionDeskList(List<DeskSection> deskSectionList);
    }

    interface Presenter extends BasePresenter {
        void loadAttentionDeskList(List<DeskSection> deskList);

        List<DeskSection> getDeletedDeskList(List<DeskSection> deskList, int position);
    }
}
