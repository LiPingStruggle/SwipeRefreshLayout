package com.liping_struggle.testswiperefreshlayout.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liping_struggle.testswiperefreshlayout.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by struggle_liping on 2017/3/23.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private static final String TAG = BaseAdapter.class.getSimpleName();

    private List<T> mDatas = new ArrayList<>();

    private View mHeaderView;
    private View mFooterView;

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private Context mContext;

    public BaseAdapter(Context context, List<T> list) {
        init(context);
        this.mDatas = list;
    }

    public BaseAdapter(Context context) {
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        if (getHeaderViewCount() < 0) {
            return;
        }
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        if (getFooterViewCount() < 0) {
            return;
        }
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG,"lip getItemViewType"+position);
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0 && getHeaderViewCount() > 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && getFooterViewCount() > 0) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new BaseViewHolder(mFooterView);
        }
        final BaseViewHolder holder = new BaseViewHolder(mLayoutInflater.inflate(getItemViewLayout(), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition(), mDatas.get(holder.getLayoutPosition() - getHeaderViewCount()));
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition() - getHeaderViewCount());
                    return true;
                }
            });
        }
        return holder;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }


    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
            onBindItemData(holder, position - getHeaderViewCount(), mDatas.get(position - getHeaderViewCount()));
            return;
        }
    }

    //利用ViewHodler来实现addView
    class BaseViewHolder extends RecyclerViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }

        }
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 加入header和footer
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size() + getFooterViewCount() + getHeaderViewCount();
    }

    protected abstract int getFooterViewCount();

    protected abstract int getHeaderViewCount();

    protected abstract void onBindItemData(RecyclerViewHolder holder, int position, T item);

    protected abstract int getItemViewLayout();


    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int pos, T t);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }


}
