package com.sunset.commen.router.core

import android.net.Uri
import com.sunset.commen.router.model.RouteDataInfo

/**
 * Created by on 2021/3/2 13:53
 * @author szn
 * 说明：
 */

object KRouter {


    /**
     * 以path形式发起一个路由请求
     */
    fun create(path: String): RouteDataInfo {
        return RouteDataInfo(path)
    }

    /**
     * 以uri形式发起一个路由请求
     */
    fun create(uri: Uri): RouteDataInfo {
        return RouteDataInfo(uri)
    }

}