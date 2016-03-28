package com.sunzxy.asyncexecutor.core;

import android.content.Context;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhengxiaoyong on 16/3/18.
 */
public class AsyncRefManager<P> {

    private WeakReference<Context> mContextRef;

    private SparseArray<WeakReference<P>> mParamsRef;

    private AtomicInteger mAtomicInteger;

    {
        mAtomicInteger = new AtomicInteger(0);
        mParamsRef = new SparseArray<>();
    }

    @SafeVarargs
    public final void attachReference(Context context, P... ps) {
        if (context != null)
            mContextRef = new WeakReference<Context>(context);
        if (ps != null && ps.length != 0) {
            int length = ps.length;
            WeakReference<P> weakRef;
            mParamsRef.clear();
            for (int i = 0; i < length; i++) {
                weakRef = new WeakReference<P>(ps[i]);
                mParamsRef.put(mAtomicInteger.getAndIncrement(), weakRef);
            }
        }
    }

    public void attachReference(Context context) {
        if (context != null)
            mContextRef = new WeakReference<Context>(context);
    }

    public Context getContext() {
        if (mContextRef == null) {
            return null;
        }
        return mContextRef.get();
    }

    public List<P> getParams() {
        if (mParamsRef == null)
            return new ArrayList<>();
        int size = mParamsRef.size();
        List<P> ps = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ps.add(mParamsRef.get(i).get());
        }
        return ps;
    }
}
