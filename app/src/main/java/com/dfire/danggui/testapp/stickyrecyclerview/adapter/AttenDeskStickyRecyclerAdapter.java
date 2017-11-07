package com.dfire.danggui.testapp.stickyrecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dfire.danggui.testapp.R;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.Desk;
import com.dfire.danggui.testapp.stickyrecyclerview.stickyrecyclerviewheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/17.
 */

public class AttenDeskStickyRecyclerAdapter extends SectioningAdapter {
    private Context context;
    List<Desk> deskList;
    ArrayList<Section> sections = new ArrayList<>();

    private class Section {
        String alpha;
        List<Desk> deskList = new ArrayList<>();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView tvDeskName;
        ImageView ivDeskDesc;
        ImageView ivDeleteDesk;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDeskName = (TextView) itemView.findViewById(R.id.tvDeskName);
            ivDeskDesc = (ImageView) itemView.findViewById(R.id.ivDeskDesc);
            ivDeleteDesk = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView tvScopeName;
        ImageView ivDeleteScope;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvScopeName = (TextView) itemView.findViewById(R.id.tvScopeName);
            ivDeleteScope = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }

    public AttenDeskStickyRecyclerAdapter(Context context) {
        this.context = context;
    }

    public List<Desk> getDeskList() {
        return deskList;
    }

    public void setDeskList(List<Desk> deskList) {
        this.deskList = deskList;
        sections.clear();
        char alpha = 0;
        Section currentSection = null;
        for (Desk desk : deskList) {
            if (desk.getDeskName().charAt(0) != alpha) {
                if (currentSection != null) {
                    sections.add(currentSection);
                }
                currentSection = new Section();
                alpha = desk.getDeskName().charAt(0);
                currentSection.alpha = String.valueOf(alpha);
            }
            if (currentSection != null) {
                currentSection.deskList.add(desk);
            }
        }
        sections.add(currentSection);
        notifyAllSectionsDataSetChanged();
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).deskList.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_sticky_recyclerview_item_view, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_sticky_recyclerview_view_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = sections.get(sectionIndex);
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        final Desk desk = s.deskList.get(itemIndex);
        ivh.tvDeskName.setText(desk.getDeskName());
        ivh.ivDeleteDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, desk.getDeskName() + " deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        final Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;

        hvh.tvScopeName.setText(s.alpha);
        hvh.ivDeleteScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, s.alpha + " deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
