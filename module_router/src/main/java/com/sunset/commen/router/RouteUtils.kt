package com.sunset.commen.router

/**
 * Created by on 2021/3/2 15:28
 * @author szn
 * 说明：
 */

object RouteUtils {

    fun getRoutePath(url: String): String{
        if(url.contains("://")){
            val str = url.substring(url.indexOf("://") + 3)
            return str.substring(str.indexOf("/"))
        }
        return url
    }

}