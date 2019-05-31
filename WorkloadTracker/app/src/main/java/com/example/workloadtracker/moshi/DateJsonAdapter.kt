package com.kidslox.app.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter {

    @FromJson
    fun fromJson(json: String?): Date? {
        return json?.let {
            try {
                SimpleDateFormat("dd/MM/yyyy").parse(json)
            } catch (e: ParseException) {
                SimpleDateFormat("dd/MM/yyyy").parse(json)
            }
        }
    }

    @ToJson
    fun toJson(date: Date?): String? {
        return date?.let { SimpleDateFormat("yyyy-MM-dd").format(date) }
    }
}