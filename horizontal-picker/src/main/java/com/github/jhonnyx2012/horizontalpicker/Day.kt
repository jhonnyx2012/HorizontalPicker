package com.github.jhonnyx2012.horizontalpicker

import org.joda.time.DateTime
import java.util.*

/**
 * Created by jhonny on 28/02/2017.
 */
class Day(private val date: DateTime) {
    var isSelected = false

    private var monthPattern = "MMMM YYYY"

    val day: String
        get() = date.dayOfMonth.toString()

    val weekDay: String
        get() = date.toString("EEE", Locale.getDefault()).uppercase(Locale.getDefault())

    fun getMonth(pattern: String): String {
        if (pattern.isNotEmpty()) monthPattern = pattern
        return date.toString(monthPattern, Locale.getDefault())
    }

    fun getDate(): DateTime {
        return date.withTime(0, 0, 0, 0)
    }

    val isToday: Boolean
        get() {
            val today = DateTime().withTime(0, 0, 0, 0)
            return getDate().millis == today.millis
        }
}