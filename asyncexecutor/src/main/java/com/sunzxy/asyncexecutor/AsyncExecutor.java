package com.sunzxy.asyncexecutor;

import com.sunzxy.asyncexecutor.callback.IUICallback;
import com.sunzxy.asyncexecutor.core.AsyncCallable;
import com.sunzxy.asyncexecutor.core.AsyncFutureTask;
import com.sunzxy.asyncexecutor.core.AsyncThreadFactory;
import com.sunzxy.asyncexecutor.core.AsyncUtils;

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

    public static <P, R> Future<?> post(AsyncCallable<P, R> runnable, IUICallback<P, R> uiCallback, boolean autoRef) {
        AsyncUtils.paramCheck(runnable);
        if (autoRef) {
            AsyncUtils.cleanInnerRef(runnable);
            AsyncUtils.cleanInnerRef(uiCallback);
        }
        return mExecutor.submit(new AsyncFutureTask<P, R>(runnable, uiCallback));
    }
    public static <P, R> Future<?> post(AsyncCallable<P, R> runnable,IUICallback<P, R> uiCallback) {
        return post(runnable, uiCallback, false);
    }
    public static <P, R> Future<?> post(AsyncCallable<P, R> runnable) {
        return post(runnable, null, false);
    }

    public static <P, R> Future<?> postAtFront(AsyncCallable<P, R> runnable) {
        return postAtFront(runnable, null, false);
    }

    public static <P, R> Future<?> post(AsyncCallable<P, R> runnable, boolean autoRef) {
        return post(runnable, null, autoRef);
    }

    public static <P, R> Future<?> postAtFront(AsyncCallable<P, R> runnable, boolean autoRef) {
        return postAtFront(runnable, null, autoRef);
    }

    public static <P, R> Future<?> postAtFront(AsyncCallable<P, R> runnable, IUICallback<P, R> uiCallback, boolean autoRef) {
        AsyncUtils.paramCheck(runnable);
        if (autoRef) {
            AsyncUtils.cleanInnerRef(runnable);
            AsyncUtils.cleanInnerRef(uiCallback);
        }
        AsyncFutureTask<P, R> task = new AsyncFutureTask<P, R>(runnable, uiCallback);
        task.setPriority(AsyncFutureTask.HIGH_PRIORITY);
        return mExecutor.submit(task);
    }
    public static <P, R> Future<?> postAtFront(AsyncCallable<P, R> runnable, IUICallback<P, R> uiCallback) {
        return postAtFront(runnable,uiCallback,false);
    }

}
