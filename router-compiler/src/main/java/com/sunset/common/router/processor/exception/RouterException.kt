package com.sunset.common.router.processor.exception

import java.lang.RuntimeException
import javax.lang.model.element.Element

/**
 * Created by on 2021/3/1 16:57
 * @author szn
 * 说明：
 */

class RouterException : RuntimeException{

    private var element: Element? = null

    constructor(message: String, element: Element) : super(message){
        this.element = element
    }

    constructor(message: String?, throwable: Throwable): super(message, throwable)

    constructor(message: String?, throwable: Throwable, element: Element): super(message, throwable){
        this.element = element
    }

    fun getElement() = element

}