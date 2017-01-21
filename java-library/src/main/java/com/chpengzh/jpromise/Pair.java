package com.chpengzh.jpromise;

/*********************************************************************
 * This file is part of seeyoutime project
 * Created by chpengzh@seeyoutime.com on 2016/10/19.
 * Copyright (c) 2016 XingDian Co.,Ltd. - All Rights Reserved
 *********************************************************************/
public final class Pair<T1, T2> {

    public final T1 first;
    public final T2 second;

    public Pair(T1 t1, T2 t2) {
        this.first = t1;
        this.second = t2;
    }
}
