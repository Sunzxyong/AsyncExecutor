package com.sunzxy.asyncexecutor;

import com.sunzxy.asyncexecutor.core.AsyncFutureTask;
import com.sunzxy.asyncexecutor.core.AsyncThreadFactory;
import com.sunzxy.asyncexecutor.core.AsyncCallable;
import com.sunzxy.asyncexecutor.callback.IUICallback;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhengxiaoyong on 16/3/14.
 */
public class AsyncExecutor {

    private static final ThreadPoolExecutor mExecutor;

    static {
        mExecutor = AsyncThreadFactory.getAsyncExecutor();
    }

    private AsyncExecutor() {
    }

    public static <P, R> Future<?> post(AsyncCallable<P,R> runnable, IUICallback<P,R> uiCallback) {
        paramCheck(runnable);
        return mExecutor.submit(new AsyncFutureTask<P,R>(runnable, uiCallback));
    }

    public static <P,R> Future<?> post(AsyncCallable<P,R> runnable) {
        return post(runnable, null);
    }

    public static <P,R> Future<?> postAtFront(AsyncCallable<P,R> runnable) {
        return postAtFront(runnable, null);
    }

    public static <P,R> Future<?> postAtFront(AsyncCallable<P,R> runnable, IUICallback<P,R> uiCallback) {
        paramCheck(runnable);
        AsyncFutureTask<P,R> task = new AsyncFutureTask<P,R>(runnable, uiCallback);
        task.setPriority(AsyncFutureTask.HIGH_PRIORITY);
        return mExecutor.submit(task);
    }

    public static <T> void paramCheck(T t) {
        if (t == null) {
            throw new NullPointerException("param is null");
        }
    }

}
