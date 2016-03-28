package com.sunzxy.asyncexecutor.callback;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhengxiaoyong on 16/3/15.
 */
public class UIThreadExecutor {

    private static Handler mHandler;

    public static <P, R> void postToUIThread(final R r, final IUICallback<P, R> uiCallback) {
        enableUIThread();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (uiCallback != null)
                    uiCallback.onCallback(r);
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
