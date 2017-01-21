package com.chpengzh.kpromise

import android.util.Log

/*********************************************************************
 * This file is part of seeyoutime project
 * Created by chpengzh@seeyoutime.com on 16-9-17.
 * Copyright (c) 2016 XingDian Co.,Ltd. - All Rights Reserved
 *********************************************************************/
abstract class KPromise<out T> {

    abstract fun <R> then(desc: String = "<undefine>", task: (T, KReturn<R>) -> Unit): KPromise<R>

    abstract fun execute()

    companion object {
        fun <T> start(group: String, task: (KReturn<T>) -> Unit): KPromise<T> =
                KPromiseImpl<Any, T>(group = group, step = 0, task = { void, ret -> task(ret) },
                        last = null, description = "<start>")
    }
}

interface KReturn<in R> {
    fun next(data: R)
}

internal class KPromiseImpl<in T1, T2>(val group: String, val step: Int,
                                       private val description: String = "<no description>",
                                       private val task: (T1, KReturn<T2>) -> Unit,
                                       private val last: KPromiseImpl<*, T1>?) : KPromise<T2>() {

    var next: KPromiseImpl<T2, *>? = null

    override fun <R> then(desc: String, task: (T2, KReturn<R>) -> Unit): KPromise<R> {
        val ret = KPromiseImpl(group = group, step = step + 1, task = task,
                last = this, description = desc)
        next = ret as KPromiseImpl<T2, *>
        return ret
    }

    fun next(data: Any) {
        @Suppress("UNCHECKED_CAST")
        task(data as T1, object : KReturn<T2> {
            override fun next(data: T2) {
                if (next != null) {
                    Log.d("KPromise", "Promise: <$group> ${next!!.step}:${next!!.description}")
                    next!!.next(data!!)
                }
            }
        })
    }

    override fun execute() {
        if (last != null) last.execute() else next(Any())
    }
}