package com.sunset.common.router.processor.model

import com.squareup.kotlinpoet.TypeName
import com.sunset.common.router.processor.Constants
import javax.lang.model.type.TypeMirror

/**
 * Created by on 2021/3/1 18:01
 * @author szn
 * 说明：
 */
data class RouteModelData(
    val path: String,
    val type: RouteType,
    val classType: TypeMirror,
    val interceptors: MutableList<TypeName>
)


enum class RouteType(val type: String){
    ACTIVITY(Constants.ACTIVITY)
}
