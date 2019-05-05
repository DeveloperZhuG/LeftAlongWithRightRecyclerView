package com.zhu.leftalongwithrightrecyclerview.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zhu.leftalongwithrightrecyclerview.R;
import com.zhu.leftalongwithrightrecyclerview.httpbean.HostMenuBean;
import com.zhu.leftalongwithrightrecyclerview.httpbean.HttpResponseBean;
import com.zhu.leftalongwithrightrecyclerview.httpbean.SubMenuBean;
import com.zhu.leftalongwithrightrecyclerview.rv.host.HostRvChangeListener;
import com.zhu.leftalongwithrightrecyclerview.rv.sub.ItemHeaderDecoration;
import com.zhu.leftalongwithrightrecyclerview.rv.host.RecyclerViewAdapter;
import com.zhu.leftalongwithrightrecyclerview.rv.sub.RightDetailRVAdapter;
import com.zhu.leftalongwithrightrecyclerview.rv.sub.RightMenuBean;
import com.zhu.leftalongwithrightrecyclerview.rv.RvItemOnSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoubleLinkedRecyclerView extends FrameLayout {

    private Context mContext;

    private HttpResponseBean mHttpResponseBean;

    private boolean mIsRvHostMoving = false;

    private boolean mIsRvSubMoving = false;

    private int mRvSubCountMoving = 0;

    private RecyclerView mRecyclerViewHost;
    private RecyclerView mRecyclerViewSub;
    private RecyclerViewAdapter mHostAdapter;
    private RightDetailRVAdapter mSubAdapter;
    private GridLayoutManager mSubGridLayoutManagerSub;
    private LinearLayoutManager mHostLinearLayoutManager;
    private ItemHeaderDecoration mItemHeaderDecoration;
    private int targetPosition;

    public DoubleLinkedRecyclerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DoubleLinkedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
        init();
    }

    public DoubleLinkedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        mContext = context;
        init();
    }

    public DoubleLinkedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.double_rv_layout, this, true);

        initHostRecyclerView();
        initSubRecyclerView();
    }

    private void initSubRecyclerView() {
        mRecyclerViewSub = findViewById(R.id.rv_right);
        mSubAdapter = new RightDetailRVAdapter(R.layout.rv_item);
        mRecyclerViewSub.setAdapter(mSubAdapter);
        mSubGridLayoutManagerSub = new GridLayoutManager(mContext, 1);
        mRecyclerViewSub.setLayoutManager(mSubGridLayoutManagerSub);
        mRecyclerViewSub.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mIsRvSubMoving && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mIsRvSubMoving = false;
                    int n = mRvSubCountMoving - mSubGridLayoutManagerSub.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRecyclerViewSub.getChildCount()) {
                        int top = mRecyclerViewSub.getChildAt(n).getTop();
                        mRecyclerViewSub.smoothScrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsRvSubMoving) {
                    mIsRvSubMoving = false;
                    int n = mRvSubCountMoving - mSubGridLayoutManagerSub.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRecyclerViewSub.getChildCount()) {
                        int top = mRecyclerViewSub.getChildAt(n).getTop();
                        mRecyclerViewSub.scrollBy(0, top);
                    }
                }
            }
        });
        mItemHeaderDecoration = new ItemHeaderDecoration(mContext, new ArrayList<RightMenuBean>());
        mRecyclerViewSub.addItemDecoration(mItemHeaderDecoration);

        mItemHeaderDecoration.setCheckListener(new HostRvChangeListener() {
            @Override
            public void onHostItemSelectChange(int position) {
                if (mRecyclerViewSub != null) {
                    if (mIsRvHostMoving) {
                        mIsRvHostMoving = false;
                    } else {
                        mHostAdapter.setCheckedPosition(position);
                        mHostAdapter.notifyDataSetChanged();
                    }
                    ItemHeaderDecoration.setCurrentTag(String.valueOf(position));
                }
            }
        });
    }

    private void initHostRecyclerView() {
        mRecyclerViewHost = findViewById(R.id.rv_left);
        mHostAdapter = new RecyclerViewAdapter(R.layout.rv_item, new RvItemOnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (mRecyclerViewSub != null) {
                    mIsRvHostMoving = true;
                    targetPosition = position;
                    setHostMenuItemChecked(position);
                    ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));
                }
            }
        });
        mRecyclerViewHost.setAdapter(mHostAdapter);
        mHostLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewHost.setLayoutManager(mHostLinearLayoutManager);
    }

    private void setHostMenuItemChecked(int position) {
        mHostAdapter.setCheckedPosition(position);
        mHostAdapter.notifyDataSetChanged();

        mRvSubCountMoving = getCountMoving(position);
        scrollSubMenu(mRvSubCountMoving);
    }

    private int getCountMoving(int position) {
        int count = 0;
        for (int i = 0; i < position; i++) {
            count += mHttpResponseBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
        }
        count += position;
        return count;
    }

    public void setData(HttpResponseBean responseBean) {
        mHttpResponseBean = responseBean;

        List<String> leftMenus = getHostMenuData(responseBean);
        mHostAdapter.setNewData(leftMenus);

        List<RightMenuBean> subMenuBean = getSubMenuBean(responseBean);
        mSubAdapter.setNewData(subMenuBean);
        mItemHeaderDecoration.setData(subMenuBean);
    }

    @NotNull
    private List<String> getHostMenuData(HttpResponseBean responseBean) {
        List<HostMenuBean> hostMenuBeans = responseBean.getCategoryOneArray();
        List<String> leftMenus = new ArrayList<>();
        for (int i = 0; i < hostMenuBeans.size(); i++) {
            leftMenus.add(hostMenuBeans.get(i).getName());
        }
        return leftMenus;
    }

    private List<RightMenuBean> getSubMenuBean(HttpResponseBean responseBean) {
        List<RightMenuBean> subMenuBeans = new ArrayList<>();
        List<HostMenuBean> hostMenuBeans = responseBean.getCategoryOneArray();
        for (int i = 0; i < hostMenuBeans.size(); i++) {
            RightMenuBean rightMenuBeanHead = new RightMenuBean();
            rightMenuBeanHead.setTitle(true);
            rightMenuBeanHead.setName(hostMenuBeans.get(i).getName());
            rightMenuBeanHead.setTitleName(hostMenuBeans.get(i).getName());
            rightMenuBeanHead.setTag(String.valueOf(i));
            subMenuBeans.add(rightMenuBeanHead);
            List<SubMenuBean> categoryTwoArray = hostMenuBeans.get(i).getCategoryTwoArray();
            for (int j = 0; j < categoryTwoArray.size(); j++) {
                RightMenuBean rightMenuBeanContent = new RightMenuBean();
                rightMenuBeanContent.setTitle(false);
                rightMenuBeanContent.setName(categoryTwoArray.get(j).getName());
                rightMenuBeanContent.setTitleName(hostMenuBeans.get(i).getName());
                rightMenuBeanContent.setTag(String.valueOf(i));
                subMenuBeans.add(rightMenuBeanContent);
            }
        }
        return subMenuBeans;
    }


    public void scrollSubMenu(int position) {
        mRvSubCountMoving = position;
        mRecyclerViewSub.stopScroll();
        smoothMoveSubRvToPosition(position);
    }


    private void smoothMoveSubRvToPosition(int n) {
        int firstItem = mSubGridLayoutManagerSub.findFirstVisibleItemPosition();
        int lastItem = mSubGridLayoutManagerSub.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerViewSub.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerViewSub.getChildAt(n - firstItem).getTop();
            mRecyclerViewSub.scrollBy(0, top);
        } else {
            mRecyclerViewSub.scrollToPosition(n);
            mIsRvSubMoving = true;
        }
    }
}
