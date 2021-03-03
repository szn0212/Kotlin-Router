package com.szntest.testrouter

import android.app.Application
import com.sunset.commen.router.startRouter
import com.sunset.common.router.annotation.RouteConfig

/**
 * Created by on 2021/2/25 13:22
 * @author szn
 * 说明：
 */
@RouteConfig(schema = "trouter", host = "com.szntest.router")
class App : Application(){


    companion object{
        val isLogin = false
    }

    override fun onCreate() {
        super.onCreate()

        startRouter(applicationContext){
            loadRouteCreator(RouterRuleCreator())
        }
    }

}