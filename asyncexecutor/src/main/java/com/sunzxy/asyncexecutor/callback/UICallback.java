package com.sunzxy.asyncexecutor.callback;

import android.content.Context;

import com.sunzxy.asyncexecutor.core.AsyncRefManager;

import java.util.List;

/**
 * Created by zhengxiaoyong on 16/3/17.
 */
public abstract class UICallback<Param, Result> implements IUICallback<Param, Result> {
    private AsyncRefManager<Param> mAsyncRefManager;

    {
        mAsyncRefManager = new AsyncRefManager<>();
    }

    public UICallback() {
    }

    public UICallback(Context context) {
        mAsyncRefManager.attachReference(context);
    }

    @SafeVarargs
    public UICallback(Param... params) {
        mAsyncRefManager.attachReference(null,params);
    }

    @SafeVarargs
    public UICallback(Context context, Param... params) {
        mAsyncRefManager.attachReference(context,params);
    }

    @Override
    public void onCallback(Result result) {
        onResult(result);
    }

    protected abstract void onResult(Result result);

    protected Context getContext() {
        return mAsyncRefManager.getContext();
    }

    protected List<Param> getParams() {
        return mAsyncRefManager.getParams();
    }
}
