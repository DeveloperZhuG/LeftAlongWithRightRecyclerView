package com.zhu.leftalongwithrightrecyclerview.rv;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;

public class RecyclerViewAdapter extends BaseQuickAdapter<String, TextViewHolder> {

    private int mCheckedPosition;

    private RvItemOnSelectedListener mRvItemOnSelectedListener;

    public void setCheckedPosition(int checkedPosition) {
        mCheckedPosition = checkedPosition;
    }

    public RecyclerViewAdapter(int layoutResId, RvItemOnSelectedListener rvItemOnSelectedListener) {
        super(layoutResId);
        mRvItemOnSelectedListener = rvItemOnSelectedListener;
    }

    @Override
    protected void convert(TextViewHolder helper, String item) {
        helper.setRvItemOnSelectedListener(new RvItemOnSelectedListener() {
            @Override
            public void onSelected(int position) {
                mRvItemOnSelectedListener.onSelected(position);
            }
        });
        helper.setMenuData(item);

        String textColorStr = helper.getLayoutPosition() == mCheckedPosition ? "#B9A173" : "#333333";
        int color = Color.parseColor(textColorStr);
        helper.changeTextColor(color);
    }
}
