package com.liping_struggle.testswiperefreshlayout;

import android.view.View;

/**
 * Created by struggle_liping on 2017/8/11.
 */

public interface ISuperRefresh <T extends View>{

    /**
     * 获取刷新方向
     * @return
     */
    int getPullToRefreshScrollDirection();

    /**
     * 生成刷新view可以是任意View
     * @return
     */
    T createRefreshableView();

    /**
     * 开始准备刷新
     * @return
     */
    boolean isReadyForPullStart();

    /**
     * 准备结束刷新
     * @return
     */
    boolean isReadyForPullEnd();
}
