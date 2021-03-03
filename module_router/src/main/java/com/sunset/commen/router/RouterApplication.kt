package com.sunset.commen.router

import android.app.Application
import android.content.Context
import com.sunset.commen.router.core.RouteTable
import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.route.ActivityRouteCreator
import com.sunset.commen.router.route.RouterCreator
import java.util.*

/**
 * Created by on 2021/3/2 14:32
 * @author szn
 * 说明：
 */

typealias RouterAppDeclaration = RouterApplication.() -> Unit

fun startRouter(applicationContext: Context, appDeclaration: RouterAppDeclaration) : RouterApplication{
    val application = RouterApplication.init(applicationContext)
    appDeclaration.invoke(application)
    return application
}


class RouterApplication private constructor(){

    lateinit var applicationContext: Context

    fun loadRouteCreator(creator : RouterCreator) : RouterApplication {
        RouteTable.routes.putAll(creator.createActivityRouteRules())
        RouteTable.interceptors.putAll(creator.createInterceptors())
        return Inner.instance
    }

    fun loadRouter(router: Pair<String, ActivityRouteCreator>) : RouterApplication {
        RouteTable.routes[router.first] = router.second
        return Inner.instance
    }

    fun loadRouter(vararg routers: Pair<String, ActivityRouteCreator>) : RouterApplication {
        routers.forEach {router ->
            RouteTable.routes[router.first] = router.second
        }
        return Inner.instance
    }

    fun loadRouters(routers: Map<String, ActivityRouteCreator>) : RouterApplication {
        RouteTable.routes.putAll(routers)
        return Inner.instance
    }

    fun loadInterceptor(interceptor: Pair<Int, Class<out IInterceptor>>) : RouterApplication {
        RouteTable.interceptors[interceptor.first] = interceptor.second
        return Inner.instance
    }

    fun loadInterceptor(vararg interceptors: Pair<Int, Class<out IInterceptor>>) : RouterApplication {
        interceptors.forEach {interceptor ->
            RouteTable.interceptors[interceptor.first] = interceptor.second
        }
        return Inner.instance
    }

    fun loadInterceptors(interceptors: TreeMap<Int, Class<out IInterceptor>>) : RouterApplication {
        RouteTable.interceptors.putAll(interceptors)
        return Inner.instance
    }

    internal object Inner{
        val instance = RouterApplication()
    }

    companion object {

        fun getInstance() = Inner.instance

        @JvmStatic
        fun init(appContext: Context): RouterApplication {
            getInstance().applicationContext = appContext as? Application ?: appContext.applicationContext
            return getInstance()
        }
    }

}