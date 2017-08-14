package com.liping_struggle.testswiperefreshlayout.base;

import android.view.View;

/**
 * Created by struggle_liping on 2017/8/13.
 */

public interface IFooterViewHolder<VH> {
    VH addFooterView(View footerView);
    int getFooterResId();
}
