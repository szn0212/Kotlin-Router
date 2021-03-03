package com.sunset.commen.router.core

import android.util.Log
import com.sunset.commen.router.RouteUtils
import com.sunset.commen.router.RouterApplication
import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.iinterface.InterceptorCallback
import com.sunset.commen.router.model.InterceptorInfo
import com.sunset.commen.router.model.RouteDataInfo
import com.sunset.commen.router.route.ActivityRouteCreator
import java.lang.Exception

/**
 * Created by on 2021/2/22 15:52
 * @author szn
 * 说明：
 */

internal class Router private constructor(){

    val TAG = Router.javaClass.simpleName

    internal object Inner{
        val instance = Router()
    }

    companion object{
        fun getInstance() = Inner.instance
    }

    fun route(routeData: RouteDataInfo){
        Log.i(TAG, ">>> RouteTable.routes size is ${RouteTable.routes.size}")
        val routerMap = RouteTable.routes.filterKeys {
            Log.i(TAG, ">>> RouteTable.routes keys $it , path = ${RouteUtils.getRoutePath(it)}, current to path is ${routeData.path}<<<")
            RouteUtils.getRoutePath(it) == routeData.path
        }
        if(routerMap.isNullOrEmpty()){
            Log.i(TAG, ">>> route path ${routeData.path} Not Found! <<<")
            routeData.routeFailedCallback?.invoke(routeData, "")
            return
        }


        val handler = routerMap.values.first()
//        val isIntercept = isIntercept(handler)
//        if(isIntercept){
//            //无拦截处理
//        } else {
//            //有拦截处理
//
//
//        }

        routerMap.values.forEach { activityRouteCreator ->
            routeData.routeBeforeCallback?.invoke(routeData, activityRouteCreator.clazz.name)

            try {
                val handler = createHandler(activityRouteCreator)
                handler.handle(RouterApplication.getInstance().applicationContext, routeData)
            } catch (e : Exception){
                e.printStackTrace()
                routeData.routeFailedCallback?.invoke(routeData, activityRouteCreator.clazz.name)
            }

        }


    }


//    private fun isIntercept(routeCreator: ActivityRouteCreator, routeData: RouteDataInfo): Boolean{
//        routeCreator.interceptors?.forEach {clz ->
//            RouteTable.interceptors.asSequence().filter {
//                it.value.name == clz.name
//            }.forEach {
//                val interceptor = clz.newInstance() as IInterceptor
//                InterceptorInfo(interceptor).setPriority(it.key).setInterceptorCallback(object : InterceptorCallback{
//                    override fun onContinue(routeData: RouteDataInfo) {
//                        //继续
//                    }
//
//                    override fun onInterrupt(e: Throwable) {
//                        //被拦截
//                    }
//
//                })
//                interceptor.process(routeData, routeData)
//            }
//        }
//    }

}