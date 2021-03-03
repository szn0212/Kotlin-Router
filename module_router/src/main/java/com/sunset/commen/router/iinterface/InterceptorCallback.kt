package com.sunset.commen.router.iinterface

import com.sunset.commen.router.model.RouteDataInfo
import kotlin.jvm.Throws

/**
 * Created by on 2021/3/2 10:57
 * @author szn
 * 说明：
 */
interface InterceptorCallback {

    /**
     * 未被拦截，继续操作
     */
    fun onContinue(routeData: RouteDataInfo)

    /**
     * 被拦截
     */
    fun onInterrupt(e: Throwable)

}