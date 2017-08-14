package com.liping_struggle.testswiperefreshlayout.base;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.liping_struggle.testswiperefreshlayout.R;
import com.liping_struggle.testswiperefreshlayout.swiperefresh.SwipeRefresh;

/**
 * Created by struggle_liping on 2017/8/12.
 */

public class PullRefreshLayout extends FrameLayout implements SwipeRefresh.OnRefreshListener,IFooterViewHolder<FootertViewHolder> {


    private static final String TAG = PullRefreshLayout.class.getSimpleName();
    protected SwipeRefresh mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnRefreshListener mOnRefreshListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private BaseRecyclerAdapter mAdapter;
    private int[] mColor;
    private View mView;
    private boolean mRefreshing;
    private FootertViewHolder mFootertViewHolder;
    private boolean isRefreshLoadMore;

    public void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
        mSwipeRefreshLayout.setRefreshing(mRefreshing);
    }


    public void setRefreshLoadMore(boolean refreshLoadMore) {
        isRefreshLoadMore = refreshLoadMore;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public FootertViewHolder addFooterView(View footerView) {
        mFootertViewHolder = new FootertViewHolder(footerView);
        return mFootertViewHolder;
    }

    @Override
    public int getFooterResId() {
        return R.layout.footer_view;
    }


    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }


    public PullRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public PullRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mView =LayoutInflater.from(getContext()).inflate(R.layout.refresh_layout,this);
        mSwipeRefreshLayout = (SwipeRefresh) mView.findViewById(R.id.base_swiperefresh);
        mRecyclerView = (RecyclerView)mView. findViewById(R.id.base_recyclerview);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;
                Log.d(TAG, "lip onScrollStateChanged: lastVisible"+linearLayoutManager.findLastVisibleItemPosition());
                Log.d(TAG, "lip onScrollStateChanged: firstVisible"+linearLayoutManager.findFirstVisibleItemPosition());
                Log.d(TAG, "lip onScrollStateChanged: itemCount"+linearLayoutManager.getItemCount());
                Log.d(TAG, "lip onScrollStateChanged: childCount"+linearLayoutManager.getChildCount());
                Log.d(TAG, "lip onScrollStateChanged: complete"+linearLayoutManager.findLastCompletelyVisibleItemPosition());
                if (checkIsLoadMore(newState)){
                    if (mFootertViewHolder!=null) {
                        mFootertViewHolder.showMore();
                    }
                    if(mOnLoadMoreListener!=null){
                        mOnLoadMoreListener.onLoadMore();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private boolean checkIsLoadMore(int newState) {
        LinearLayoutManager linearLayoutManager= (LinearLayoutManager) mLayoutManager;
        Log.d(TAG, "checkIsLoadMore: totalCount"+mAdapter.getTotalCount()+","+linearLayoutManager.findLastVisibleItemPosition());
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            if (hasLoadMore()){
               return true;
            }
        }
        return false;
    }

    public boolean hasLoadMore(){
        LinearLayoutManager linearLayoutManager= (LinearLayoutManager) mLayoutManager;
      if (isRefreshLoadMore && mOnLoadMoreListener!=null && linearLayoutManager.findLastVisibleItemPosition()==linearLayoutManager.getItemCount()-1){
          return true;
      }
      return false;
    }

    public void setColorSchemeColors(@ColorInt int... colors) {
        this.mColor = colors;
        mSwipeRefreshLayout.setColorSchemeColors(mColor);
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        mAdapter = adapter;
        if (mAdapter==null){
            throw  new NullPointerException("adapter Don't for empty");
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setFooterViewHolder(this);
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void onStop(){
        mFootertViewHolder.hideMore();
    }

    @Override
    public void onRefresh() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }
}
