package com.liping_struggle.testswiperefreshlayout.base;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liping_struggle.testswiperefreshlayout.R;

import java.util.List;

/**
 * Created by struggle_liping on 2017/8/12.
 */

public abstract class BaseListActivity<T> extends BaseActivity implements PullRefreshLayout.OnRefreshListener, PullRefreshLayout.OnLoadMoreListener {


    protected PullRefreshLayout mPullRefreshLayout;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;



    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mBaseRecyclerAdapter.setData(getData(+3));
                    mPullRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    mBaseRecyclerAdapter.setData(getData(+8));
                    mPullRefreshLayout.onStop();
                    break;
            }

        }
    };

    protected abstract List getData(int i);


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPullRefreshLayout = (PullRefreshLayout) findViewById(R.id.base_pullrefreshlayout);
        mPullRefreshLayout.setOnRefreshListener(this);
        mPullRefreshLayout.setOnLoadMoreListener(this);
        mPullRefreshLayout.setLayoutManager(getLayoutManager());
        mPullRefreshLayout.setColorSchemeColors(getRefreshColors());
        mPullRefreshLayout.addItemDecoration(getItemDecoration());
        mPullRefreshLayout.setRefreshLoadMore(true);

    }
    protected int[] getRefreshColors() {
        return new int[]{Color.YELLOW,Color.RED,Color.GREEN,Color.BLUE};
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getApplicationContext());
    }



    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
    }


    @Override
    protected void initData() {
        mBaseRecyclerAdapter = getAdapter();
        mPullRefreshLayout.setAdapter(mBaseRecyclerAdapter);

    }

    protected abstract BaseRecyclerAdapter getAdapter();


    @Override
    public void onRefresh() {
        mPullRefreshLayout.setRefreshing(true);
        mHandler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    public void onLoadMore() {
        mPullRefreshLayout.setRefreshing(false);
        mHandler.sendEmptyMessageDelayed(2,1000);
    }
}
