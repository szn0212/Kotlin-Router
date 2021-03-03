package com.sunset.commen.router.model

import androidx.fragment.app.Fragment
import com.sunset.commen.router.iinterface.IInterceptor
import com.sunset.commen.router.iinterface.InterceptorCallback

/**
 * Created by on 2021/3/2 17:32
 * @author szn
 * 说明：
 */

class InterceptorInfo {

    val interceptor: IInterceptor
    var priority = -1
        private set
    var callback: InterceptorCallback? = null
        private set

    internal constructor(interceptor: IInterceptor){
        this.interceptor = interceptor

    }

    fun setPriority(priority: Int) : InterceptorInfo{
        this.priority = priority
        return this
    }

    fun setInterceptorCallback(callback: InterceptorCallback): InterceptorInfo{
        this.callback = callback
        return this
    }

}