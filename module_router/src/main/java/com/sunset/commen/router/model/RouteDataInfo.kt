package com.sunset.commen.router.model

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.IInterface
import androidx.fragment.app.Fragment
import com.sunset.commen.router.core.Router
import com.sunset.commen.router.iinterface.IInterceptor

/**
 * Created by on 2021/3/2 10:56
 * @author szn
 * 说明：
 */

class RouteDataInfo {

    val path: String
    val extras = Bundle()
    var requestCode = -1
        private set
    var options: Bundle? = null
        private set
    //    var resultCallback
    var flags = 0
        private set
    var enterAnim = -1
        private set
    var exitAnim = -1
        private set

    var routeBeforeCallback : ((routeData: RouteDataInfo, className: String) -> Unit)? = null
        private set
    var routeFailedCallback: ((routeData: RouteDataInfo, className: String) -> Unit)? = null
        private set
    var routeArrivedCallback: ((routeData: RouteDataInfo, className: String) -> Unit)? = null
        private set
    var context: Context? = null
        private set
    var activity: Activity? = null
        private set
    var fragment: Fragment? = null
        private set

    var interceptors = mutableListOf<InterceptorInfo>()


    internal constructor(uri: Uri){
        if(uri.path == null){
            throw IllegalArgumentException("Router uri $uri path is null")
        }
        this.path = uri.path!!
        uri.queryParameterNames.forEach {
            extras.putString(it, uri.getQueryParameter(it))
        }
    }

    internal constructor(path: String){
        val uri = Uri.parse(path)
        uri.queryParameterNames.forEach {
            extras.putString(it, uri.getQueryParameter(it))
        }
        this.path = path
    }

    fun withExtra(bundle: Bundle) : RouteDataInfo {
        extras.putAll(bundle)
        return this
    }

    @JvmOverloads
    fun withRequestCode(activity: Activity, requestCode: Int, options: Bundle? = null): RouteDataInfo {
        this.activity = activity
        this.requestCode = requestCode
        this.options = options
        return this
    }

    @JvmOverloads
    fun withRequestCode(fragment: Fragment, requestCode: Int, options: Bundle? = null): RouteDataInfo {
        this.fragment = fragment
        this.requestCode = requestCode
        this.options = options
        return this
    }

    fun addFlag(flag: Int): RouteDataInfo {
        this.flags = this.flags or flag
        return this
    }

    fun withTransition(activity: Activity, enterAnim: Int, exitAnim: Int) : RouteDataInfo {
        this.activity = activity
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
        return this
    }

    fun subscribeBefore(block: (routeData: RouteDataInfo, className: String) -> Unit) : RouteDataInfo {
        this.routeBeforeCallback = block
        return this
    }

    fun subscribeArrived(block: (routeData: RouteDataInfo, className: String) -> Unit): RouteDataInfo {
        this.routeArrivedCallback = block
        return this
    }

    fun subscribeFailed(block: (routeData: RouteDataInfo, className: String) -> Unit): RouteDataInfo {
        this.routeFailedCallback = block
        return this
    }

    fun addInterceptors(vararg interceptorInfo: InterceptorInfo){
        interceptors.addAll(interceptorInfo.asList())
    }

    fun request(){
        return Router.getInstance().route(this)
    }

}