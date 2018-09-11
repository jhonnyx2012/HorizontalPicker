package com.github.jhonnyx2012.horizontalpicker;


import org.threeten.bp.LocalDateTime;

/**
 * Created by jhonn on 02/03/2017.
 */
public interface DatePickerListener {
    void onDateSelected(LocalDateTime dateSelected);
}