package com.sunset.common.router.processor.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.sunset.common.router.processor.Constants
import com.sunset.common.router.processor.model.RouteModelData
import java.util.*
import javax.lang.model.type.TypeMirror

/**
 * Created by on 2021/3/1 18:11
 * @author szn
 * 说明：
 */

class RouteCodeBuilder(
    val fileName: String,
    val modelDataList: MutableList<RouteModelData>,
    val treeMaps: TreeMap<Int, TypeMirror>
){

    val routeCreator = ClassName(Constants.PACKAGENAME_ROUTE_MODULE, Constants.CLASSNAME_ROUTE_CREATOR)
    val activityRouteCreator = ClassName(Constants.PACKAGENAME_ROUTE_MODULE, Constants.CLASSNAME_ACTIVITY_ROUTE_CREATOR)

    fun build() = TypeSpec.classBuilder(fileName)
        .addSuperinterface(routeCreator)
        .addRouterMethod()
        .addInterceptorMethod()
        .build()


    private fun TypeSpec.Builder.addRouterMethod() = apply {
        val builder = FunSpec.builder("createActivityRouteRules")
            .addModifiers(KModifier.OVERRIDE)
            .returns(MAP.parameterizedBy(STRING, activityRouteCreator))
            .addStatement("val routes = mutableMapOf<%T, %T>()", STRING, activityRouteCreator)

        modelDataList.forEach { modelData ->
            val codeBuilder = CodeBlock.builder()
                .add("routes.put(%S, %T(%T::class.java)", modelData.path, activityRouteCreator, modelData.classType)
            modelData.interceptors.forEach {
                codeBuilder.add(".addInterceptor(%T::class.java)", it)
            }
            codeBuilder.addStatement(")")
            builder.addCode(codeBuilder.build())
        }

        builder.addStatement("return routes")

        addFunction(builder.build())
    }

    private fun TypeSpec.Builder.addInterceptorMethod() = apply {
        val treeMap = ClassName("java.util", "TreeMap")
        val interceptorName = ClassName("com.sunset.commen.router.iinterface", "IInterceptor")
        val className = ClassName("java.lang", "Class").parameterizedBy(WildcardTypeName.producerOf(interceptorName))
        val builder = FunSpec.builder("createInterceptors")
            .addModifiers(KModifier.OVERRIDE)
            .returns(treeMap.parameterizedBy(INT, className))
            .addStatement("val interceptors = %T<%T, %T>()", treeMap, INT, className)

        treeMaps.forEach {
            builder.addStatement("interceptors.put(%L, %T::class.java)", it.key, it.value)
        }

        builder.addStatement("return interceptors")

        addFunction(builder.build())
    }
}