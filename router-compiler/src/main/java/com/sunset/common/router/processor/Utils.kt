package com.sunset.common.router.processor

/**
 * Created by on 2021/3/1 18:04
 * @author szn
 * 说明：
 */

object Utils {

    fun getRouteUrl(schema: String, host: String, path: String) : String{
        return "$schema://$host$path"
    }
}