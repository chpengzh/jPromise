package com.chpengzh.jpromise;

import android.support.annotation.NonNull;

public abstract class Starter<R> implements Task<Void, R> {

    public abstract void onStart(@NonNull Return<R> start);

    @Override
    public final void onTask(Void data, @NonNull Return<R> ret) {
        onStart(ret);
    }
}