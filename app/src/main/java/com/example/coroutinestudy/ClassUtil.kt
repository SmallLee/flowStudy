package com.example.coroutinestudy

import java.lang.reflect.*

object ClassUtil {

    fun <T> getTClass(t: T): Class<out Any>? {
        val genType: Type = t!!.javaClass.genericSuperclass as Type
        val params =
            (genType as ParameterizedType).actualTypeArguments
        val type = params[0]
        val finalNeedType: Type = if (params.size > 1) {
            check(type is ParameterizedType) { "Generic parameter not filled" }
            type.actualTypeArguments[0]
        } else {
            type
        }
        return getClass(finalNeedType, 0)
    }

    fun getClass(type: Type?, i: Int): Class<out Any>? {
        return when (type) {
            is ParameterizedType -> { // handle generic types
                (type as ParameterizedType?)?.let { getGenericClass(it, i) }
            }
            is TypeVariable<*> -> {
                getClass(type.bounds[0], 0) // handle the generic wipe object
            }
            else -> { // The class itself is also a type, and the cast is forced
                type as Class<*>?
            }
        }
    }

    fun getGenericClass(parameterizedType: ParameterizedType, i: Int): Class<out Any>? {
        return when (val genericClass: Any = parameterizedType.actualTypeArguments[i]) {
            is ParameterizedType -> { // handle multilevel generics
                genericClass.rawType as Class<*>
            }
            is GenericArrayType -> { // handle array generics
                genericClass.genericComponentType as Class<*>
            }
            is TypeVariable<*> -> { //
                // handle the generic wipe object
                getClass(genericClass.bounds[0], 0)
            }
            is WildcardType -> {
                getClass(genericClass.upperBounds[0], 0)
            }
            else -> {
                genericClass as Class<*>
            }
        }
    }
}