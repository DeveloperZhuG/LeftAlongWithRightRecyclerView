package com.zhu.leftalongwithrightrecyclerview.rv.sub;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhu.leftalongwithrightrecyclerview.R;
import com.zhu.leftalongwithrightrecyclerview.rv.host.HostRvChangeListener;

import java.util.List;


public class ItemHeaderDecoration extends RecyclerView.ItemDecoration {
    private int mTitleHeight;
    private List<RightMenuBean> mDatas;
    private LayoutInflater mInflater;

    public static String currentTag = "0";//标记当前左侧选中的position，因为有可能选中的item，右侧不能置顶，所以强制替换掉当前的tag
    private HostRvChangeListener mCheckListener;


    public void setCheckListener(HostRvChangeListener checkListener) {
        mCheckListener = checkListener;
    }

    public static void setCurrentTag(String currentTag) {
        ItemHeaderDecoration.currentTag = currentTag;
    }

    public ItemHeaderDecoration(Context context, List<RightMenuBean> datas) {
        this.mDatas = datas;
        Paint paint = new Paint();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int titleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        paint.setTextSize(titleFontSize);
        paint.setAntiAlias(true);
        mInflater = LayoutInflater.from(context);
    }


    public ItemHeaderDecoration setData(List<RightMenuBean> data) {
        mDatas.clear();
        mDatas.addAll(data);
        return this;
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull final RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mDatas.isEmpty()) {
            return;
        }
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        drawHeader(parent, pos, canvas);

        checkHostItem(pos);
    }

    private void checkHostItem(int pos) {
        String tag = mDatas.get(pos).getTag();
        if (!TextUtils.equals(tag, currentTag)) {
            currentTag = tag;
            mCheckListener.onHostItemSelectChange(Integer.valueOf(tag));
        }
    }

    /**
     * @param parent
     * @param pos
     */
    private void drawHeader(RecyclerView parent, int pos, Canvas canvas) {
        View topTitleView = mInflater.inflate(R.layout.rv_item, parent, false);
        TextView tvTitle = topTitleView.findViewById(R.id.tv_menu);
        tvTitle.setText(mDatas.get(pos).getTitleName());

        //绘制title开始
        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) topTitleView.getLayoutParams();
        if (lp == null) {
            lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
        }

        topTitleView.setLayoutParams(lp);
        toDrawWidthSpec = getMeasuredSpec(parent, lp.width);
        toDrawHeightSpec = getMeasuredSpec(parent, lp.height);
        performProcessDisplay(parent, canvas, topTitleView, toDrawWidthSpec, toDrawHeightSpec);

    }

    private void performProcessDisplay(RecyclerView parent, Canvas canvas, View topTitleView, int toDrawWidthSpec, int toDrawHeightSpec) {
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上
        topTitleView.measure(toDrawWidthSpec, toDrawHeightSpec);
        topTitleView.layout(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getPaddingLeft() + topTitleView.getMeasuredWidth(), parent.getPaddingTop() + topTitleView.getMeasuredHeight());
        topTitleView.draw(canvas);//Canvas默认在视图顶部，无需平移，直接绘制
        //绘制title结束
    }

    private int getMeasuredSpec(RecyclerView parent, int widthOrHeight) {
        int toDrawWidthSpec;
        if (widthOrHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (widthOrHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(widthOrHeight, View.MeasureSpec.EXACTLY);
        }
        return toDrawWidthSpec;
    }
}
