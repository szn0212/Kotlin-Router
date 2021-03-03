package com.sunset.common.router.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Route(
    /**
     * path need start with /
     */
    val path: String,
)
