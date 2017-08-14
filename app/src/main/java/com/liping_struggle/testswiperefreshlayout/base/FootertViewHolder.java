package com.liping_struggle.testswiperefreshlayout.base;

import android.view.View;
import android.widget.TextView;

import com.liping_struggle.testswiperefreshlayout.R;

public class FootertViewHolder extends BaseRecyclerViewHolder {

    private TextView mTextView;

    public void showMore(){
        mTextView.setVisibility(View.VISIBLE);
    }

    public void hideMore(){
        mTextView.setVisibility(View.GONE);
    }


    public FootertViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.loadmore);
    }
}