package com.zhu.leftalongwithrightrecyclerview.rv;

import android.graphics.Color;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;

public class RecyclerViewAdapter extends BaseQuickAdapter<String, TextViewHolder> {

    private int mCheckedPosition;

    private RvItemOnSelectedListener mRvItemOnSelectedListener;

    public void setCheckedPosition(int checkedPosition) {
        mCheckedPosition = checkedPosition;
        Log.d("Test", "checkedPosition: " + mCheckedPosition, new Exception());
    }

    public RecyclerViewAdapter(int layoutResId, RvItemOnSelectedListener rvItemOnSelectedListener) {
        super(layoutResId);
        mRvItemOnSelectedListener = rvItemOnSelectedListener;
    }

    @Override
    protected void convert(TextViewHolder helper, String item) {
        helper.setRvItemOnSelectedListener(mRvItemOnSelectedListener);
        helper.setMenuData(item);

        String textColorStr = helper.getLayoutPosition() == mCheckedPosition ? "#B9A173" : "#333333";
        int color = Color.parseColor(textColorStr);
        helper.changeTextColor(color);
    }
}
