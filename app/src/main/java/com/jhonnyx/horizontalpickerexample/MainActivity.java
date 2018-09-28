package com.jhonnyx.horizontalpickerexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;


public class MainActivity extends AppCompatActivity implements DatePickerListener {

  private LocalDate date;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidThreeTen.init(this);
    setContentView(R.layout.activity_main);
    final HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.datePicker);
    picker
        .setDays(365)
        .setOffset(365/2)
        .setScrollToSelectedPosition(true)
        .setDateSelectedColor(Color.DKGRAY)
        .setDateSelectedTextColor(Color.WHITE)
        .setMonthAndYearTextColor(Color.DKGRAY)
        .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
        .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
        .setTodayDateBackgroundColor(Color.GRAY)
        .setUnselectedDayTextColor(Color.DKGRAY)
        .setDayOfWeekTextColor(Color.DKGRAY)
        .setUnselectedDayTextColor(getResources().getColor(R.color.primaryTextColor))
        .showTodayButton(false)
        .init();
    picker.setBackgroundColor(Color.LTGRAY);
    date = LocalDate.now();
    picker.setDate(date);
    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //date = date.plusMonths(1);
        picker.setDate(date);
      }
    });
  }

  @Override
  public void onDateSelected(LocalDate dateSelected) {
    Log.i("HorizontalPicker", "Fecha seleccionada=" + dateSelected.toString());
  }
}
