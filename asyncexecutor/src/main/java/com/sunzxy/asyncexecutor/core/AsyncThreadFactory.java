package com.sunzxy.asyncexecutor.core;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengxiaoyong on 16/3/14.
 */
public class AsyncThreadFactory {

    private AsyncThreadFactory() {
        throw new RuntimeException("can not be a instance");
    }

    public static ThreadPoolExecutor getAsyncExecutor() {
        int cupNum = Runtime.getRuntime().availableProcessors() * 2;//IOBusiness
        return new ThreadPoolExecutor(
                cupNum,
                cupNum,
                0L,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(),
                new PriorityThreadFactory());
    }

}
