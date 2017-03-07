package com.jhonnyx.horizontalpickerexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.jhonnyx.horizontalpicker.DatePickerListener;
import com.jhonnyx.horizontalpicker.HorizontalPicker;
import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity implements DatePickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker
                .setListener(this)
                .setDays(120)
                .setOffset(7)
                .init();
        picker.setDate(new DateTime());
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());
    }
}
