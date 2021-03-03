package com.sunset.common.router.processor

import com.squareup.kotlinpoet.*
import com.sunset.common.router.annotation.Interceptor
import com.sunset.common.router.processor.exception.RouterException
import com.sunset.common.router.processor.model.BasicConfiguration
import com.sunset.common.router.annotation.Route
import com.sunset.common.router.annotation.RouteConfig
import com.sunset.common.router.annotation.RouteInterceptor
import com.sunset.common.router.processor.builder.RouteCodeBuilder
import com.sunset.common.router.processor.model.RouteModelData
import com.sunset.common.router.processor.model.RouteType
import java.io.File
import java.lang.Exception
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic
import kotlin.jvm.Throws

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(RouteConfig::class.java.canonicalName, Route::class.java.canonicalName)

    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv?.let {
            try {
                val config = processRouteConfig(roundEnv)
                if(config == null){
                    error(message = "The class you are annotated by RouteConfig must be a Application")
                    return true
                }
                processRouteRules(roundEnv, config)
                return false
            } catch (e: RouterException){
                e.printStackTrace()
                error(e.getElement(), e.message)
            }
        }
        return true
    }

    @Throws(Exception::class)
    private fun processRouteConfig(roundEnv: RoundEnvironment): BasicConfiguration? {
        var configuration : BasicConfiguration? = null
        try {
            val type_application = processingEnv.elementUtils.getTypeElement(Constants.APPLICATION).asType()
            roundEnv.getElementsAnnotatedWith(RouteConfig::class.java)?.forEach {
                if(configuration != null){
                    throw RouterException("The RouteConfig in this module was defined duplicated!", it)
                }
                if(processingEnv.typeUtils.isSubtype(it.asType(), type_application)){
                    val config = it.getAnnotation(RouteConfig::class.java)
                    if(!config.schema.isNullOrEmpty()){
                        var host = config.host
                        val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                        configuration = BasicConfiguration(config.schema, if(host.isNullOrEmpty()) pack else host, pack)
                        testProcessor(pack, "RouteConfigClass")
                        return configuration
                    }
                } else {
                    throw RouterException("The RouteConfig must be modified Application", it)
                }
            }
        } catch (e: RouterException){
            throw e
        } catch (e: Throwable){
            throw RouterException(e.message, e)
        }
        return configuration
    }

    @Throws(Exception::class)
    private fun processRouteRules(roundEnv: RoundEnvironment, config: BasicConfiguration) {
        val modelDataList = mutableListOf<RouteModelData>()
        try {
            val type_activity = processingEnv.elementUtils.getTypeElement(Constants.ACTIVITY).asType()
            roundEnv.getElementsAnnotatedWith(Route::class.java)
                ?.forEach {
                    testProcessor(processingEnv.elementUtils.getPackageOf(it).toString(), "TestAdapter")
                    if(!it.modifiers.contains(Modifier.ABSTRACT) and !it.modifiers.contains(Modifier.PRIVATE)){
                        val route = it.getAnnotation(Route::class.java)

                        val packageName = processingEnv.elementUtils.getPackageOf(it).toString()
                        val className = it.simpleName.toString()
                        if(processingEnv.typeUtils.isSubtype(it.asType(), type_activity)){
                            modelDataList.add(RouteModelData(Utils.getRouteUrl(config.schema, config.host, route.path),
                                RouteType.ACTIVITY, it.asType(), combineInterceptors(it)))
                            testProcessor(packageName, "A$className")
                        }
                    } else {
                        throw RouterException("The Route Annotation not modified abstract and private", it)
                    }
                }

            val treeMap = obtainInterceptors(roundEnv)
            val fileName = "RouterRuleCreator"
            FileSpec.builder(config.packageName, fileName)
                .addType(RouteCodeBuilder(fileName, modelDataList, treeMap).build())
                .build()
                .writeFile()
        } catch (e: RouterException){
            throw e
        } catch (e: Throwable){
            throw RouterException(e.message, e)
        }

    }

    private fun combineInterceptors(type: Element): MutableList<TypeName> {
        val interceptor = type.getAnnotation(RouteInterceptor::class.java)
        val interceptors = mutableListOf<TypeName>()
        try {
            interceptor?.interceptor?.forEach {clz -> interceptors.add(clz.asClassName()) }
        } catch (mirrored : MirroredTypesException){
            mirrored.typeMirrors.forEach {
                interceptors.add(it.asTypeName())
            }
        }
        return interceptors
    }

    private fun obtainInterceptors(roundEnv: RoundEnvironment): TreeMap<Int, TypeMirror> {
        val treeMap = TreeMap<Int, TypeMirror>()
        try {
            val type_interceptor = processingEnv.elementUtils.getTypeElement(Constants.INTERCEPTOR).asType()
            roundEnv.getElementsAnnotatedWith(Interceptor::class.java)
                ?.forEach {
                    testProcessor(processingEnv.elementUtils.getPackageOf(it).toString(), "A${it.simpleName.toString()}")
                    val interceptor = it.getAnnotation(Interceptor::class.java)
                    treeMap[interceptor.priority] = it.asType()
                }
        } catch ( e: RouterException){
            throw e
        } catch (e: Exception){
            throw RouterException(e.message, e)
        }

        return treeMap
    }

    private fun processInterceptorRules(roundEnv: RoundEnvironment){
        roundEnv.getElementsAnnotatedWith(Interceptor::class.java)?.forEach {
            testProcessor(processingEnv.elementUtils.getPackageOf(it).toString(), "TestInterceptor")
            val interceptor = it.getAnnotation(Interceptor::class.java)
            val className = it.simpleName.toString()
        }
    }

    private fun testProcessor(packageName: String, fileName: String){
        FileSpec.builder(packageName, fileName)
            .addType(
                TypeSpec.classBuilder(fileName)
                    .build())
            .build()
            .writeFile()
    }

    fun FileSpec.writeFile() {
        val kaptKotlinGeneratedDir = processingEnv.options[Constants.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        writeTo(File(kaptKotlinGeneratedDir))
    }

    private fun error(element: Element? = null, message: String? = null) {
        if(element != null && message?.isNotEmpty() == true){
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message, element)
        }

    }

}