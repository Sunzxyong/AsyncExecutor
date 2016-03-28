package com.sunzxy.asyncexecutor.callback;

import android.content.Context;

import com.sunzxy.asyncexecutor.core.AsyncRefManager;

import java.util.List;

/**
 * Created by zhengxiaoyong on 16/3/17.
 */
public abstract class UICallback<P, R> implements IUICallback<P, R> {
    private AsyncRefManager<P> mAsyncRefManager;

    {
        mAsyncRefManager = new AsyncRefManager<>();
    }

    public UICallback() {
    }

    public UICallback(Context context) {
        mAsyncRefManager.attachReference(context);
    }

    @SafeVarargs
    public UICallback(P... ps) {
        mAsyncRefManager.attachReference(null, ps);
    }

    @SafeVarargs
    public UICallback(Context context, P... ps) {
        mAsyncRefManager.attachReference(context, ps);
    }

    @Override
    public void onCallback(R r) {
        onResult(r);
    }

    protected abstract void onResult(R r);

    protected Context getContext() {
        return mAsyncRefManager.getContext();
    }

    protected List<P> getParams() {
        return mAsyncRefManager.getParams();
    }
}
