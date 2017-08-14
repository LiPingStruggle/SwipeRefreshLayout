package com.liping_struggle.testswiperefreshlayout.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T,VH extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<VH> {


    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private int mTotalCount;
    private List<T> mListData;
    private View mHeaderView;
    private View mFooterView;
    private IFooterViewHolder<VH> mFooterViewHolder;



    public void setFooterViewHolder(IFooterViewHolder<VH> footerViewHolder) {
        mFooterViewHolder = footerViewHolder;
    }


    public BaseRecyclerAdapter(Context context, List<T> listData) {
        mContext = context;
        mListData = listData!=null?listData:new ArrayList<T>();
        mTotalCount += mListData.size();
    }

    public BaseRecyclerAdapter() {
    }


    @Override
    public int getItemViewType(int position) {
        Log.d(TAG,"lip getItemViewType"+position);
//        if (position == 0 && getHeaderViewCount() > 0) {
//            //第一个item应该加载Header
//            return TYPE_HEADER;
//        }
        if (position == getItemCount() - 1 && mFooterViewHolder!=null) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER && mFooterViewHolder !=null && mFooterViewHolder.getFooterResId()!=0) {
            View mFooterView = LayoutInflater.from(mContext).inflate(mFooterViewHolder.getFooterResId(), parent, false);
            return  mFooterViewHolder.addFooterView(mFooterView);
        }
        View itemView = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
        return getViewHolder(itemView);
    }


    protected abstract VH getViewHolder(View itemView);



    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position)==TYPE_NORMAL) {
            onBind(holder, mListData.get(position));
        }
    }

    public void setData(List<T> listData) {
        mListData = listData!=null?listData:new ArrayList<T>();
        mTotalCount+=mListData.size();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListData.size()+(mFooterViewHolder!=null?1:0);
    }


    public abstract void onBind(VH holder, T item);

    public abstract int getItemLayout();

    public int getTotalCount() {
        return mTotalCount;
    }
}