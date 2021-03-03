package com.sunset.common.router.annotation

/**
 * Created by on 2021/2/25 16:01
 * @author szn
 * 说明：
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RouteConfig(
    val schema: String,
    val host: String = ""
)