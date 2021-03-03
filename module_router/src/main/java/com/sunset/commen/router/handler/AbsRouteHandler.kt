package com.sunset.commen.router.handler

import android.content.Context
import com.sunset.commen.router.model.RouteDataInfo
import com.sunset.commen.router.route.ActivityRouteCreator
import java.lang.Exception
import kotlin.jvm.Throws

/**
 * Created by on 2021/3/2 17:58
 * @author szn
 * 说明：
 */

internal abstract class AbsRouteHandler(val routeCreator: ActivityRouteCreator){

    @Throws(Exception::class)
    abstract fun handle(context: Context, routeData: RouteDataInfo)

}