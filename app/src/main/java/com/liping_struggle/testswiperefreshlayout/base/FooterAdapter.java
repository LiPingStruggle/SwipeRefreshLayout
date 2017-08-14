package com.liping_struggle.testswiperefreshlayout.base;

import android.view.View;

import com.liping_struggle.testswiperefreshlayout.R;

/**
 * Created by struggle_liping on 2017/8/13.
 */

public class FooterAdapter extends BaseRecyclerAdapter<String,BaseRecyclerViewHolder> {


    private FootertViewHolder mFootertViewHolder;


    @Override
    protected BaseRecyclerViewHolder getViewHolder(View itemView) {
        mFootertViewHolder = new FootertViewHolder(itemView);
        return mFootertViewHolder;
    }

    public FootertViewHolder getFootertViewHolder() {
        return mFootertViewHolder;
    }


    @Override
    public void onBind(BaseRecyclerViewHolder holder, String item) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.footer_view;
    }



}
