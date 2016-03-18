package com.sunzxy.asyncexecutor.callback;

/**
 * Created by zhengxiaoyong on 16/3/15.
 */
public interface IUICallback<Param, Result> {

    /**
     * @param result  The computes result.Nullable
     */
    void onCallback(Result result);
}
