package com.sunzxy.asyncexecutor.core;

import com.sunzxy.asyncexecutor.callback.IUICallback;
import com.sunzxy.asyncexecutor.callback.UIThreadExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by zhengxiaoyong on 16/3/15.
 */
public class AsyncFutureTask<Param, Result> extends FutureTask<Result> implements Comparable<AsyncFutureTask> {
    public static final int HIGH_PRIORITY = 10;
    public static final int NORMAL_PRIORITY = 5;
    private IUICallback<Param, Result> mUICallback;
    private int mPriority = NORMAL_PRIORITY;

    public AsyncFutureTask(Callable<Result> callable, IUICallback<Param, Result> uiCallback) {
        super(callable);
        this.mUICallback = uiCallback;
    }

    public AsyncFutureTask(Runnable runnable, Result result) {
        super(runnable, result);
    }

    @Override
    protected void done() {
        super.done();
        //task is done,cancel|exception|finish
    }

    @Override
    protected void set(Result result) {
        super.set(result);
        taskDone();
    }

    @Override
    protected void setException(Throwable t) {
        super.setException(t);
        taskDone();
    }

    /**
     * only finish|exception
     */
    protected void taskDone() {
        if (mUICallback == null)
            return;
        try {
            Result result = get();
            UIThreadExecutor.postToUIThread(result, mUICallback);
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            e.printStackTrace();
            UIThreadExecutor.postToUIThread(null, mUICallback);
        }
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public int getPriority() {
        return mPriority;
    }

    @Override
    public int compareTo(AsyncFutureTask another) {
        int my = this.getPriority();
        int other = another.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }
}
