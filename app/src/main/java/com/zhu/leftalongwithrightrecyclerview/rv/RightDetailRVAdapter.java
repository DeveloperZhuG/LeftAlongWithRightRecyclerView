package com.zhu.leftalongwithrightrecyclerview.rv;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;

public class RightDetailRVAdapter extends BaseQuickAdapter<RightMenuBean, TextViewHolder> {

    public RightDetailRVAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(TextViewHolder helper, RightMenuBean item) {
        helper.setMenuData(item.getName());
    }
}
