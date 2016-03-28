package com.sunzxy.asyncexecutor.core;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by zhengxiaoyong on 16/3/14.
 */
public abstract class AsyncCallable<P, R> implements Callable<R> {
    private AsyncRefManager<P> mAsyncRefManager;

    {
        mAsyncRefManager = new AsyncRefManager<>();
    }

    public AsyncCallable() {
    }

    public AsyncCallable(Context context) {
        mAsyncRefManager.attachReference(context);
    }

    @SafeVarargs
    public AsyncCallable(P... ps) {
        mAsyncRefManager.attachReference(null, ps);
    }

    @SafeVarargs
    public AsyncCallable(Context context, P... ps) {
        mAsyncRefManager.attachReference(context, ps);
    }

    @Override
    public R call() throws Exception {
        return run();
    }

    protected abstract R run();

    protected Context getContext() {
        return mAsyncRefManager.getContext();
    }

    protected List<P> getParams() {
        return mAsyncRefManager.getParams();
    }

}
