package com.github.jhonnyx2012.horizontalpicker;

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

    private static final int NO_SETTED = -1;
    private View vHover;
    private TextView tvMonth;
    private TextView tvToday;
    private DatePickerListener listener;
    private HorizontalPickerRecyclerView rvDays;
    private int days;
    private int offset;

    public HorizontalPicker(Context context) {
        super(context);
        internInit();
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        internInit();

    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        internInit();
    }

    private void internInit() {
        this.days = NO_SETTED;
        this.offset = NO_SETTED;
    }

    public HorizontalPicker setListener(DatePickerListener listener)
    {
        this.listener=listener;
        return this;
    }

    public void setDate(final DateTime date)
    {
        rvDays.post(new Runnable() {
            @Override
            public void run() {
                rvDays.setDate(date);
            }
        });
    }


    public void init() {
        inflate(getContext(),R.layout.horizontal_picker,this);
        rvDays = (HorizontalPickerRecyclerView) findViewById(R.id.rvDays);
        int DEFAULT_DAYS_TO_PLUS = 120;
        int finalDays=days==NO_SETTED? DEFAULT_DAYS_TO_PLUS :days;
        int DEFAULT_INITIAL_OFFSET = 7;
        int finalOffset=offset==NO_SETTED? DEFAULT_INITIAL_OFFSET :offset;
        vHover=findViewById(R.id.vHover);
        tvMonth=(TextView) findViewById(R.id.tvMonth);
        tvToday=(TextView) findViewById(R.id.tvToday);
        rvDays.setListener(this);
        tvToday.setOnClickListener(rvDays);
        rvDays.init(getContext(),finalDays,finalOffset);
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

    public HorizontalPicker setDays(int days) {
        this.days = days;
        return this;
    }

    public int getDays() {
        return days;
    }

    public HorizontalPicker setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getOffset() {
        return offset;
    }
}
