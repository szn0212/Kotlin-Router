package com.sunset.commen.router.route

import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.route.ActivityRouteCreator
import java.util.*

/**
 * Created by on 2021/2/24 15:36
 * @author szn
 * 说明：
 */

interface RouterCreator{

    fun createActivityRouteRules(): Map<String, ActivityRouteCreator>

    fun createInterceptors(): TreeMap<Int, Class<out IInterceptor>>

}
