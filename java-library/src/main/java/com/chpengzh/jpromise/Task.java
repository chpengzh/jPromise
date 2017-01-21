package com.chpengzh.jpromise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Task<T, R> {
    void onTask(@Nullable T data, @NonNull final Return<R> ret);
}

