package com.chpengzh.kpromise

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log

/*********************************************************************
 * This file is part of seeyoutime project
 * Created by chpengzh@seeyoutime.com on 2017/1/21.
 * Copyright (c) 2016 XingDian Co.,Ltd. - All Rights Reserved
 *********************************************************************/
class DemoActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

}