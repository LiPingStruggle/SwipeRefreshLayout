package com.liping_struggle.testswiperefreshlayout;

import com.liping_struggle.testswiperefreshlayout.base.BaseListActivity;
import com.liping_struggle.testswiperefreshlayout.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseListActivity<String> {


    private List<String> mData = new ArrayList<>();

    @Override
    protected BaseRecyclerAdapter<String, TestAdapter.TestViewHolder> getAdapter() {
        return new TestAdapter(this,getData(10));
    }

    public List<String> getData(int index) {
        for (int i = 0; i < index; i++) {
            mData.add("Test"+i);
        }
        return mData;
    }



}
