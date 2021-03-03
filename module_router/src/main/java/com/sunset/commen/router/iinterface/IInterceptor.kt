package com.sunset.commen.router.iinterface

import com.sunset.commen.router.model.RouteDataInfo

/**
 * Created by on 2021/3/2 10:54
 * @author szn
 * 说明：
 */

interface IInterceptor {

    fun process(routeData: RouteDataInfo, callback: InterceptorCallback)

}