package com.sunzxy.asyncexecutor.callback;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhengxiaoyong on 16/3/15.
 */
public class UIThreadExecutor {

    private static Handler mHandler;

    public static <Param, Result> void postToUIThread(final Result result, final IUICallback<Param, Result> uiCallback) {
        enableUIThread();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (uiCallback != null)
                    uiCallback.onCallback(result);
            }
        });
    }

    private static void enableUIThread() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == mHandler.getLooper().getThread();
    }

}
