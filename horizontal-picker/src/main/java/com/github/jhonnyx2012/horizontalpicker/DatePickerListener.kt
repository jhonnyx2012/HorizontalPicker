package com.github.jhonnyx2012.horizontalpicker

import org.joda.time.DateTime

/**
 * Created by jhonny on 02/03/2017.
 */
interface DatePickerListener {
    fun onDateSelected(dateSelected: DateTime)
}