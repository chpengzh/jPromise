package com.chpengzh.jpromise;

import android.support.annotation.Nullable;

public interface Return<R> {
    void next(@Nullable R data);
}