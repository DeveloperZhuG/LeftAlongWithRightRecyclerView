package com.zhu.leftalongwithrightrecyclerview.rv.sub;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhu.leftalongwithrightrecyclerview.R;
import com.zhu.leftalongwithrightrecyclerview.rv.TextViewHolder;

public class RightDetailRVAdapter extends BaseQuickAdapter<RightMenuBean, TextViewHolder> {

    public RightDetailRVAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(TextViewHolder helper, RightMenuBean item) {
        if (item.isTitle()) {
            helper.setTextColor(R.id.tv_menu, Color.parseColor("#FF00BCD4"));
        }else {
            helper.setTextColor(R.id.tv_menu, Color.parseColor("#000000"));
        }
        helper.setMenuData(item.getName());
    }
}
