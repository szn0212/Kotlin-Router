package com.sunset.commen.router.core

import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.route.ActivityRouteCreator
import java.util.*

/**
 * Created by on 2021/3/2 14:13
 * @author szn
 * 说明：
 */

object RouteTable {

    val routes = mutableMapOf<String, ActivityRouteCreator>()
    val interceptors = TreeMap<Int, Class<out IInterceptor>>()
}

