package com.szntest.testrouter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunset.common.router.annotation.Route

/**
 * Created by on 2021/3/2 13:40
 * @author szn
 * 说明：
 */

@Route("/app/test2")
class Test2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}