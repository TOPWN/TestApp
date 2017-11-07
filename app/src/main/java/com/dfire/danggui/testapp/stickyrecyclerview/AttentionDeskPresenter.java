package com.dfire.danggui.testapp.stickyrecyclerview;

import com.dfire.danggui.testapp.data.DataServer;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.DeskSection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public class AttentionDeskPresenter implements AttentionDeskContract.Presenter {
    private AttentionDeskContract.View view;

    public AttentionDeskPresenter(AttentionDeskContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadAttentionDeskList(List<DeskSection> deskList) {
        // TODO: 2016/12/19 加载已经关注的桌位列表
        deskList.addAll(DataServer.getAttenDeskSampleData());
        view.showAttentionDeskList(deskList);
    }

    @Override
    public List<DeskSection> getDeletedDeskList(List<DeskSection> deskList, int position) {
        List<DeskSection> deleteDeskList = new ArrayList<>();
        deleteDeskList.add(deskList.get(position));
        for (int i = position + 1; i < deskList.size(); i++) {
            if (!deskList.get(i).isHeader) {
                deleteDeskList.add(deskList.get(i));
            } else {
                break;
            }
        }
        return deleteDeskList;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
