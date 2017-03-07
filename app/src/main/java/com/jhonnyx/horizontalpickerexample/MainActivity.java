package com.jhonnyx.horizontalpickerexample;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jhonnyx.horizontalpicker.DatePickerListener;
import com.jhonnyx.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity implements DatePickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker.setListener(this);
        picker.post(new Runnable() {
            @Override
            public void run() {
                picker.setDate(new DateTime().plusMonths(1));
            }
        });
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("EXAMPLE","FECHA SELECCIONADA="+dateSelected.toString());
    }
}
