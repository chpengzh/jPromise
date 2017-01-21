package com.chpengzh.jpromise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/*********************************************************************
 * This file is part of seeyoutime project
 * Created by chpengzh@seeyoutime.com on 16-9-17.
 * Copyright (c) 2016 XingDian Co.,Ltd. - All Rights Reserved
 *********************************************************************/
public abstract class Promise<T1> {

    @NonNull
    public static <T> Promise<T> start(@NonNull String group, @NonNull Starter<T> starter) {
        Log.d("JPromise", "Promise: <" + group + "> 0:<start>");
        return new PromiseImpl<>(group, 0, starter, "<start>", null);
    }

    public abstract String getGroup();

    public abstract int getStep();

    public abstract <T2> Promise<T2> then(@NonNull Task<T1, T2> task);

    public abstract <T2> Promise<T2> then(@NonNull String description, @NonNull Task<T1, T2> task);

    public abstract void execute();

    private static class PromiseImpl<T1, T2> extends Promise<T2> implements Return<T1> {

        private final String group;
        private final int step;
        private final Task<T1, T2> task;
        private final String description;
        private final PromiseImpl<?, T1> last;

        PromiseImpl<T2, ?> next;

        PromiseImpl(@NonNull String group, int step, @NonNull Task<T1, T2> task,
                    @Nullable String description, @Nullable PromiseImpl<?, T1> last) {
            this.group = group;
            this.step = step;
            this.task = task;
            this.description = description;
            this.last = last;
        }

        @Override
        public String getGroup() {
            return group;
        }

        @Override
        public int getStep() {
            return step;
        }

        @Override
        public <T3> Promise<T3> then(@NonNull Task<T2, T3> task) {
            return then("<no description>", task);
        }

        @Override
        public <T3> Promise<T3> then(@NonNull String description, @NonNull Task<T2, T3> task) {
            Promise<T3> ret = new PromiseImpl<>(group, step + 1, task, description, this);
            next = (PromiseImpl<T2, ?>) ret;
            return ret;
        }

        @Override
        public void next(@Nullable T1 data) {
            task.onTask(data, new Return<T2>() {
                @Override
                public void next(T2 data) {
                    if (next == null) return;
                    Log.d("JPromise", "Promise: <" + getGroup() + "> " +
                            next.getStep() + ":" + next.description);
                    next.next(data);
                }
            });
        }

        @Override
        public void execute() {
            if (last != null)
                last.execute();
            else
                next(null);
        }
    }
}
