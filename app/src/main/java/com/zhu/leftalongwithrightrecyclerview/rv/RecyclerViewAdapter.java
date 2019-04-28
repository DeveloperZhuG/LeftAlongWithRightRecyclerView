package com.zhu.leftalongwithrightrecyclerview.rv;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhu.leftalongwithrightrecyclerview.DoubleLinkedRecylcerView;

public class RecyclerViewAdapter extends BaseQuickAdapter<String, TextViewHolder> {

    private int mCheckedPosition;

    private DoubleLinkedRecylcerView mDoubleLinkedRecylcerView;

    public void setCheckedPosition(int checkedPosition) {
        mCheckedPosition = checkedPosition;
    }

    public RecyclerViewAdapter(int layoutResId, DoubleLinkedRecylcerView doubleLinkedRecylcerView) {
        super(layoutResId);
        mDoubleLinkedRecylcerView = doubleLinkedRecylcerView;
    }

    @Override
    protected void convert(TextViewHolder helper, String item) {
        helper.setRvItemOnSelectedListener(new RvItemOnSelectedListener() {
            @Override
            public void onSelected(int position) {
                setCheckedPosition(position);
                notifyDataSetChanged();

                // 把选择的 position 传到 右侧的 RV 中.
                mDoubleLinkedRecylcerView.scrollSubMenu(position);

            }
        });
        helper.setMenuData(item);

        String textColorStr = helper.getLayoutPosition() == mCheckedPosition ? "#B9A173" : "#333333";
        int color = Color.parseColor(textColorStr);
        helper.changeTextColor(color);
    }
}
