package com.sunzxy.asyncexecutor.callback;

/**
 * Created by zhengxiaoyong on 16/3/15.
 */
public interface IUICallback<P, R> {

    /**
     * @param r  The computes result.Nullable
     */
    void onCallback(R r);
}
