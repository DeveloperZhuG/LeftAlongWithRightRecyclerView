package com.zhu.leftalongwithrightrecyclerview.rv;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhu.leftalongwithrightrecyclerview.R;

public class TextViewHolder extends BaseViewHolder {

    private final TextView mTvMenuItem;

    private RvItemOnSelectedListener mRvItemOnSelectedListener;


    public void setRvItemOnSelectedListener(RvItemOnSelectedListener onSelectedListener) {
        mRvItemOnSelectedListener = onSelectedListener;
    }

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        mTvMenuItem = itemView.findViewById(R.id.tv_menu);
        mTvMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把点击事件传递到 RV 中;
                if (mRvItemOnSelectedListener != null) {
                    mRvItemOnSelectedListener.onSelected(getAdapterPosition());
                }
            }
        });
    }

    public void setMenuData(String menu) {
        mTvMenuItem.setText(menu);
    }

    public void changeTextColor(@ColorInt int color) {
        mTvMenuItem.setTextColor(color);
    }

}
