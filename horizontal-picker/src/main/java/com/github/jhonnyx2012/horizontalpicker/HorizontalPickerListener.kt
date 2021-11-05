package com.github.jhonnyx2012.horizontalpicker

/**
 * Created by jhonny on 02/03/2017.
 */
interface HorizontalPickerListener {
    fun onStopDraggingPicker()
    fun onDraggingPicker()
    fun onDateSelected(item: Day)
}