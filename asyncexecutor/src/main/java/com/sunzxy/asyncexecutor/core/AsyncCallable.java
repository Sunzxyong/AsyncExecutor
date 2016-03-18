package com.sunzxy.asyncexecutor.core;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by zhengxiaoyong on 16/3/14.
 */
public abstract class AsyncCallable<Param, Result> implements Callable<Result> {
    private AsyncRefManager<Param> mAsyncRefManager;

    {
        mAsyncRefManager = new AsyncRefManager<>();
    }

    public AsyncCallable() {
    }

    public AsyncCallable(Context context) {
        mAsyncRefManager.attachReference(context);
    }

    @SafeVarargs
    public AsyncCallable(Param... params) {
        mAsyncRefManager.attachReference(null, params);
    }

    @SafeVarargs
    public AsyncCallable(Context context, Param... params) {
        mAsyncRefManager.attachReference(context, params);
    }

    @Override
    public Result call() throws Exception {
        return run();
    }

    protected abstract Result run();

    protected Context getContext() {
        return mAsyncRefManager.getContext();
    }

    protected List<Param> getParams() {
        return mAsyncRefManager.getParams();
    }

}
