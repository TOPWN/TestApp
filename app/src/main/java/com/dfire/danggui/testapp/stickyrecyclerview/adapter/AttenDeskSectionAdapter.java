package com.dfire.danggui.testapp.stickyrecyclerview.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dfire.danggui.testapp.R;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.Desk;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.DeskSection;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AttenDeskSectionAdapter extends BaseSectionQuickAdapter<DeskSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AttenDeskSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final DeskSection item) {
        helper.setText(R.id.tvScopeName, item.header);
        helper.addOnClickListener(R.id.ivDeleteScope);
    }


    @Override
    protected void convert(BaseViewHolder helper, DeskSection item) {
        Desk desk = item.t;
        //helper.setImageUrl(R.id.iv, video.getImg());
        helper.setText(R.id.tvDeskName, desk.getDeskName());
        helper.addOnClickListener(R.id.ivDelete);
    }

    public void remove(int position, List<DeskSection> deletedData) {
        this.mData.removeAll(deletedData);
        this.notifyItemRangeRemoved(position + this.getHeaderLayoutCount(), deletedData.size());
    }
}
