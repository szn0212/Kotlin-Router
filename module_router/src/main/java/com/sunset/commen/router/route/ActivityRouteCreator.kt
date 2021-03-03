package com.sunset.commen.router.route

import android.app.Activity
import com.sunset.commen.router.iinterface.IInterceptor

/**
 * Created by on 2021/3/2 10:49
 * @author szn
 * 说明：
 */

class ActivityRouteCreator(val clazz: Class<out Activity>){

    val interceptors = mutableListOf<Class<out IInterceptor>>()

    fun addInterceptor(clazz: Class<out IInterceptor>): ActivityRouteCreator {
        interceptors.add(clazz)
        return this
    }


}