@file:JvmName("MoshiExtensions")

package com.example.workloadtracker.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

inline fun <reified T> Moshi.toJson(source: T?): String = this.adapter(T::class.java).toJson(source)

inline fun <reified T> Moshi.fromJson(source: String?, vararg componentTypes: Class<*>): T? {
    return if (source == null || source.isEmpty()) {
        null
    } else {
        val adapter: JsonAdapter<T> = if (componentTypes.isEmpty()) {
            this.adapter(T::class.java)
        } else {
            this.adapter(Types.newParameterizedType(T::class.java, *componentTypes))
        }
        adapter.nullSafe().fromJson(source)
    }
}

fun <T> Moshi.toJsonCompat(source: T?, type: Class<T>): String = this.adapter(type).toJson(source)

fun <T> Moshi.toJsonCompat(source: T?, type: Type): String = this.adapter<T>(type).toJson(source)

fun <T> Moshi.fromJsonCompat(source: String?, type: Class<T>): T? = if (source.isNullOrEmpty()) {
    null
} else {
    this.adapter(type).nullSafe().fromJson(source)
}