package com.jhonnyx.horizontalpickerexample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(), DatePickerListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val picker = findViewById<HorizontalPicker>(R.id.datePicker)
        picker.setListener(this)
            .setDays(120)
            .setOffset(7)
            .setDateSelectedColor(Color.DKGRAY)
            .setDateSelectedTextColor(Color.WHITE)
            .setMonthAndYearTextColor(Color.DKGRAY)
            .setTodayButtonTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setTodayDateTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setTodayDateBackgroundColor(Color.GRAY)
            .setUnselectedDayTextColor(Color.DKGRAY)
            .setDayOfWeekTextColor(Color.DKGRAY)
            .setUnselectedDayTextColor(ContextCompat.getColor(this, R.color.primaryTextColor))
            .showTodayButton(true)
            .initialize()
        picker.setBackgroundColor(Color.LTGRAY)
        picker.setDate(DateTime())
    }

    override fun onDateSelected(dateSelected: DateTime) {
        Log.i("HorizontalPicker", "Selected date = $dateSelected")
    }
}