package com.liping_struggle.testswiperefreshlayout;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.liping_struggle.testswiperefreshlayout.base.BaseRecyclerAdapter;
import com.liping_struggle.testswiperefreshlayout.base.BaseRecyclerViewHolder;

import java.util.List;


public class TestAdapter extends BaseRecyclerAdapter<String,TestAdapter.TestViewHolder> {


    public TestAdapter(Context context, List listData) {
        super(context, listData);
    }


    @Override
    public int getItemLayout() {
        return R.layout.item_test;
    }

    @Override
    protected TestViewHolder getViewHolder(View itemView) {
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBind(TestViewHolder holder, String item) {
        holder.mTextView.setText(item);
    }

    public static class TestViewHolder extends BaseRecyclerViewHolder{

        private TextView mTextView;

        public TestViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
