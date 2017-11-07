package com.dfire.danggui.testapp.stickyrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dfire.danggui.testapp.R;
import com.dfire.danggui.testapp.data.DataServer;
import com.dfire.danggui.testapp.stickyrecyclerview.adapter.AttenDeskSectionAdapter;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.DeskSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2016/12/17.
 */

public class StickyRecyclerActivity extends AppCompatActivity implements AttentionDeskContract.View {
    private AttenDeskSectionAdapter recyclerAdapter;
    @Bind(R.id.recyclerview)
    android.support.v7.widget.RecyclerView recyclerview;
    private List<DeskSection> deskList;

    private AttentionDeskContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recyclerview);
        ButterKnife.bind(this);
        new AttentionDeskPresenter(this);
        initView();
        initListener();
        startLoad();
    }

    private void initView() {
        deskList = new ArrayList<>();
        recyclerAdapter = new AttenDeskSectionAdapter(R.layout.activity_sticky_recyclerview_item_view, R.layout.activity_sticky_recyclerview_view_header, deskList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(recyclerAdapter);
    }

    private void initListener() {
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                DeskSection mySection = deskList.get(position);
                if (mySection.isHeader)
                    Toast.makeText(StickyRecyclerActivity.this, mySection.header, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(StickyRecyclerActivity.this, mySection.t.getDeskName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivDeleteScope:
                        DeskSection mySection = deskList.get(position);
                        Toast.makeText(StickyRecyclerActivity.this, mySection.header + " deleted " + position, Toast.LENGTH_SHORT).show();
                        recyclerAdapter.remove(position, presenter.getDeletedDeskList(deskList, position));
                        break;
                    case R.id.ivDelete:
                        DeskSection deskSection = deskList.get(position);
                        Toast.makeText(StickyRecyclerActivity.this, deskSection.t.getDeskName() + " deleted " + position, Toast.LENGTH_SHORT).show();
                        recyclerAdapter.remove(position);
                        break;
                }

            }
        });
    }

    private void startLoad() {
        presenter.loadAttentionDeskList(deskList);
    }

    @Override
    public void showAttentionDeskList(List<DeskSection> deskSectionList) {
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(AttentionDeskContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
