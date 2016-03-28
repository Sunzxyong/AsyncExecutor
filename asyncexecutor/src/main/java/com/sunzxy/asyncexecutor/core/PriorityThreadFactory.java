package com.sunzxy.asyncexecutor.core;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhengxiaoyong on 16/3/14.
 */
public class PriorityThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(final Runnable runnable) {
        Runnable wrapper = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                runnable.run();
            }
        };
        Thread thread = new Thread(wrapper, "AsyncExecutorThread");
        if (thread.isDaemon())
            thread.setDaemon(false);
        return thread;
    }
}
