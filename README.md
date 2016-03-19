# AsyncExecutor

This is an asynchronous task processing lightweight framework, using a thread pool to handle all asynchronous or lengthy operations in the application, such as: I/O, SharedPreferences, SQLite, Bitmap (decode, copy) and so on
----
[ ![Download](https://api.bintray.com/packages/sunzxyong/maven/AsyncExecutor/images/download.svg) ](https://bintray.com/sunzxyong/maven/AsyncExecutor/_latestVersion)

## Import
### Gradle

```
compile 'com.sunzxy.asyncexecutor:asyncexecutor:1.0.0'
```
### Maven

```
<dependency>
  <groupId>com.sunzxy.asyncexecutor</groupId>
  <artifactId>asyncexecutor</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
## Usage
### 1、
You can easy to use

```
        AsyncExecutor.post(new AsyncCallable<Object, String>() {
            @Override
            protected String run() {
                //compute
                return null;
            }
        });
```
Or have a UI callback

```
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
```
### 2、
If you need the incoming parameters.Such as the Context, and the View

```
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
```
You don't need to care about whether you need to use weak references to avoid memory leaks,because AsyncExecutor already all done

But AsyncExecutor still can't avoid memory leaks caused by inner class.So, you can see a third way

### 3、
But if your tasks take longer, you will need to create a static inner class for them to avoid memory leaks

```
    static class MyAsyncTask extends AsyncCallable<TextView, String> {

        public MyAsyncTask(Context context, TextView... textViews) {
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
    
    AsyncExecutor.post(new MyAsyncTask(this, textView), new MyCallback(this, textView));
```
### 4、
Parameter definition and the incoming of type

```
AsyncCallable<Param, Result>(Context ctx,Param... params)
```
And for access to the Context and parameters

```
getContext();
getParams();
```

