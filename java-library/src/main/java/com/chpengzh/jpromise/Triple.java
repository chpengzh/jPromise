package com.chpengzh.jpromise;

/*********************************************************************
 * This file is part of seeyoutime project
 * Created by chpengzh@seeyoutime.com on 2016/10/19.
 * Copyright (c) 2016 XingDian Co.,Ltd. - All Rights Reserved
 *********************************************************************/
public final class Triple<T1, T2, T3> {

    public final T1 first;
    public final T2 second;
    public final T3 third;

    public Triple(T1 t1, T2 t2, T3 t3) {
        this.first = t1;
        this.second = t2;
        this.third = t3;
    }
}
