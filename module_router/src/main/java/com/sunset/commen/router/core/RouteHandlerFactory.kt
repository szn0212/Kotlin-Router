package com.sunset.commen.router.core

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.sunset.commen.router.handler.AbsRouteHandler
import com.sunset.commen.router.model.RouteDataInfo
import com.sunset.commen.router.route.ActivityRouteCreator
import java.lang.Exception

/**
 * Created by on 2021/3/2 17:57
 * @author szn
 * 说明：
 */

internal fun createHandler(routeCreator: ActivityRouteCreator) : AbsRouteHandler {

//    return when(){
//
//    }

    return ActivityHandler(routeCreator)

}


internal class ActivityHandler(routeCreator: ActivityRouteCreator) : AbsRouteHandler(routeCreator){

    val TAG = ActivityHandler::class.simpleName

    override fun handle(context: Context, routeData: RouteDataInfo) {
        val component = ComponentName(context, routeCreator.clazz)

        val intent = Intent()
        intent.setComponent(component)
            .addFlags(routeData.flags)
            .putExtras(routeData.extras)

        Handler(Looper.getMainLooper()).post {
            try {
                when{
                    routeData.activity != null -> {
                        Log.i(TAG, ">>> handler startActivity ${routeData.path} <<<")
                        routeData.activity!!.startActivityForResult(intent, routeData.requestCode, routeData.options)
                    }
                    else -> {
                        Log.i(TAG, ">>> handler other ${routeData.path} <<<")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ActivityCompat.startActivity(context, intent, routeData.options)
                    }
                }
                routeData.routeArrivedCallback?.invoke(routeData, routeCreator.clazz.name)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

    }
}