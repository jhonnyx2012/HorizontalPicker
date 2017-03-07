package com.jhonnyx.horizontalpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

/**
 * Created by Jhonny Barrios on 22/02/2017.
 *
 */

public class HorizontalPicker extends LinearLayout implements HorizontalPickerListener{

    private View vHover;
    private TextView tvMonth;
    private TextView tvToday;
    private DatePickerListener listener;
    private HorizontalPickerRecyclerView rvDays;

    public HorizontalPicker(Context context) {
        super(context);init();
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);init();
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);init();
    }

    public HorizontalPicker setListener(DatePickerListener listener)
    {
        this.listener=listener;
        return this;
    }

    public void setDate(DateTime date)
    {
        rvDays.setDate(date);
    }

    private void init() {
        inflate(getContext(),R.layout.horizontal_picker,this);
        rvDays = (HorizontalPickerRecyclerView) findViewById(R.id.rvDays);
        vHover=findViewById(R.id.vHover);
        tvMonth=(TextView) findViewById(R.id.tvMonth);
        tvToday=(TextView) findViewById(R.id.tvToday);
        rvDays.setListener(this);
        tvToday.setOnClickListener(rvDays);
    }

    @Override
    public boolean post(Runnable action) {
        return rvDays.post(action);
    }

    @Override
    public void onStopDraggingPicker() {
        if(vHover.getVisibility()==VISIBLE)
        vHover.setVisibility(INVISIBLE);
    }

    @Override
    public void onDraggingPicker() {
        if(vHover.getVisibility()==INVISIBLE)
            vHover.setVisibility(VISIBLE);
    }

    @Override
    public void onDateSelected(Day item) {
        tvMonth.setText(item.getMonth());
        tvToday.setVisibility(item.isToday()?INVISIBLE:VISIBLE);
        if(listener!=null)
        {
            listener.onDateSelected(item.getDate());
        }
    }

}
