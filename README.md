# jPromise

`jPromise` is a simple util for async task programming in android develop. Just enjoy async task program in JS way!

## ~~Bad Format~~

```java
Lg.e("第一个任务开始了");
mHandler.postDelayed(new Runnable() {
    @Override
    public void run() {
        Lg.e("第一个任务结束了");
        Lg.e("第二个任务开始了, data = " + 1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Lg.e("第二个任务结束了");
                Lg.e("第三个任务开始了, data = " + "second");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Lg.e("第三个任务结束了， 异步任务完成");
                    }
                }, 2000);
            }
        }, 2000);
    }
}, 2000);
```

## Do it in promise way

```java
final String clientId = UUID.randomUUID().toString();
Promise.start("Some Group Name", new Starter<Integer>() {
    @Override
    public void onStart(@NonNull final Return<Integer> start) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start.next(1001);
            }
        }, 1_000);

    }
}).then("some step description here", new Task<Integer, String>() {
    @Override
    public void onTask(Integer data, @NonNull final Return<String> ret) {
        Log.d("MainActivity", "client id is " + clientId);
        Log.d("MainActivity", "data from last step is " + data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ret.next(null);
            }
        }, 1_000);

    }
}).then(new Task<String, Void>() {
    @Override
    public void onTask(@Nullable String nullable, @NonNull Return<Void> ret) {
        Log.d("DemoActivity", "data from last step is " + nullable);

    }
}).execute();
```

## Promise for Kotlin (KPromise)

```kotlin
KPromise.start<Int>("Some Group Name") { start ->
    Handler().postDelayed({
        start.next(100)
    }, 1000L)

}.then<String>("some step description here") { int, kReturn ->
    Log.d("DemoActivity", "data from last step is $int")
    Handler().postDelayed({
        kReturn.next("some string")
    }, 1000L)

}.then<Unit> { str, kReturn ->
    Log.d("DemoActivity", "data from last step is $str")

}.execute()
```

