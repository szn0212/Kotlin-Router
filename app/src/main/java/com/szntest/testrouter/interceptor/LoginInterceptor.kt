package com.szntest.testrouter.interceptor

import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.iinterface.InterceptorCallback
import com.sunset.commen.router.model.RouteDataInfo
import com.sunset.common.router.annotation.Interceptor

/**
 * Created by on 2021/2/25 13:24
 * @author szn
 * 说明：
 */
@Interceptor(name = "login", priority = 888)
class LoginInterceptor : IInterceptor {

    override fun process(routeData: RouteDataInfo, callback: InterceptorCallback) {

    }

}