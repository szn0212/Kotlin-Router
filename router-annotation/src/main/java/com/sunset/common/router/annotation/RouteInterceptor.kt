package com.sunset.common.router.annotation

import kotlin.reflect.KClass

/**
 * Created by on 2021/3/2 11:23
 * @author szn
 * 说明：
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RouteInterceptor (vararg val interceptor: KClass<out Any>)