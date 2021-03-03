package com.szntest.testrouter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sunset.commen.router.core.KRouter
import com.sunset.common.router.annotation.Route
import com.sunset.common.router.annotation.RouteInterceptor
import com.szntest.testrouter.interceptor.LoginInterceptor

/**
 * Created by on 2021/2/22 15:22
 * @author szn
 * 说明：
 */
@RouteInterceptor(LoginInterceptor::class)
@Route("/app/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title","test1")
            bundle.putInt("Index", 1)
            bundle.putString("text","当前Activity为Test1Activity，内容为test1")
            KRouter.create("/app/test1").withExtra(bundle)
                .withRequestCode(this, 1001)
                .subscribeArrived { routeData, className ->
                Log.i("MainActivity.class", ">>> subscribeArrived: route path is ${routeData.path} and className is $className <<< ")
            }.subscribeBefore { routeData, className ->
                    Log.i("MainActivity.class", ">>> subscribeBefore: route path is ${routeData.path} and className is $className <<< ")
            }.subscribeFailed { routeData, className ->
                    Log.i("MainActivity.class", ">>> subscribeFailed: route path is ${routeData.path} and className is $className <<< ")
            }.request()
        }

//        ActivityRouteRule(MainActivity::class.java)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1001 && resultCode == RESULT_OK && data != null){
            val result = data.getStringExtra("result")
            findViewById<TextView>(R.id.textView).text = result
        }
    }

}