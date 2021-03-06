package com.sunzxy.async;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.sunzxy.asyncexecutor.AsyncExecutor;
import com.sunzxy.asyncexecutor.callback.UICallback;
import com.sunzxy.asyncexecutor.core.AsyncCallable;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    Future<?> mFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.tv);
        mFuture = AsyncExecutor.post(new MyAsync(this, textView), new MyCallback(this, textView));
        AsyncExecutor.post(new AsyncCallable<Object, String>() {
            @Override
            protected String run() {
                //compute
                return null;
            }
        }, new UICallback<Object, String>() {
            @Override
            protected void onResult(String s) {

            }
        });

        AsyncExecutor.postAtFront(new AsyncCallable<TextView, String>(this, textView) {
            @Override
            public String run() {
                //The calculated results and return
                return "";
            }
        }, new UICallback<TextView, String>(textView) {
            @Override
            public void onResult(String s) {
                TextView tv = getParams().get(0);
                if (tv != null && !TextUtils.isEmpty(s))
                    tv.setText(s);
            }
        });
    }

    static class MyAsync extends AsyncCallable<TextView, String> {

        public MyAsync(Context context, TextView... textViews) {
            super(context, textViews);
        }

        @Override
        public String run() {
            SystemClock.sleep(8000);
            Context ctx = getContext();
            TextView textView = getParams().get(0);
            //...
            return "";
        }

    }

    static class MyCallback extends UICallback<TextView, String> {
        public MyCallback(Context context, TextView... textViews) {
            super(context, textViews);
        }

        @Override
        public void onResult(String s) {
            Context ctx = getContext();
            TextView textView = getParams().get(0);
            if (textView != null)
                textView.setText(s);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFuture.cancel(true);
    }
}
