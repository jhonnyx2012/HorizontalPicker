package com.github.jhonnyx2012.horizontalpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Jhonny Barrios on 22/02/2017.
 */

public class HorizontalPicker extends LinearLayout implements HorizontalPickerListener {

  private static final int NO_SETTED = -1;
  private View vHover;
  private TextView tvMonth;
  private TextView tvToday;
  private HorizontalPickerRecyclerView rvDays;
  private int days;
  private int offset;
  private int mDateSelectedColor = -1;
  private int mDateSelectedTextColor = -1;
  private int mMonthAndYearTextColor = -1;
  private int mTodayButtonTextColor = -1;
  private boolean showTodayButton = true;
  private boolean scrollToSelectedPosition;
  private String mMonthPattern = "";
  private int mTodayDateTextColor = -1;
  private int mTodayDateBackgroundColor = -1;
  private int mDayOfWeekTextColor = -1;
  private int mUnselectedDayTextColor = -1;
  private final BehaviorSubject<LocalDate> dateChangedSubject = BehaviorSubject.create();

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

  public void setDate(final LocalDate date) {
    rvDays.post(new Runnable() {
      @Override
      public void run() {
        rvDays.setDate(date);
      }
    });
  }

  public void init() {
    inflate(getContext(), R.layout.horizontal_picker, this);
    rvDays = (HorizontalPickerRecyclerView) findViewById(R.id.rvDays);
    int DEFAULT_DAYS_TO_PLUS = 120;
    int finalDays = days == NO_SETTED ? DEFAULT_DAYS_TO_PLUS : days;
    int DEFAULT_INITIAL_OFFSET = 7;
    int finalOffset = offset == NO_SETTED ? DEFAULT_INITIAL_OFFSET : offset;
    vHover = findViewById(R.id.vHover);

    tvMonth = (TextView) findViewById(R.id.tvMonth);
    tvToday = (TextView) findViewById(R.id.tvToday);
    rvDays.setListener(this);
    tvToday.setOnClickListener(rvDays);
    tvMonth.setTextColor(mMonthAndYearTextColor != -1 ? mMonthAndYearTextColor : getColor(R.color.primaryTextColor));
    tvToday.setVisibility(showTodayButton ? VISIBLE : INVISIBLE);
    tvToday.setTextColor(mTodayButtonTextColor != -1 ? mTodayButtonTextColor : getColor(R.color.colorPrimary));
    int mBackgroundColor = getBackgroundColor();
    setBackgroundColor(mBackgroundColor != Color.TRANSPARENT ? mBackgroundColor : Color.WHITE);
    mDateSelectedColor = mDateSelectedColor == -1 ? getColor(R.color.colorPrimary) : mDateSelectedColor;
    mDateSelectedTextColor = mDateSelectedTextColor == -1 ? Color.WHITE : mDateSelectedTextColor;
    mTodayDateTextColor = mTodayDateTextColor == -1 ? getColor(R.color.primaryTextColor) : mTodayDateTextColor;
    mDayOfWeekTextColor = mDayOfWeekTextColor == -1 ? getColor(R.color.secundaryTextColor) : mDayOfWeekTextColor;
    mUnselectedDayTextColor = mUnselectedDayTextColor == -1 ? getColor(R.color.primaryTextColor) : mUnselectedDayTextColor;
    rvDays.init(
        getContext(),
        finalDays,
        finalOffset,
        scrollToSelectedPosition,
        mBackgroundColor,
        mDateSelectedColor,
        mDateSelectedTextColor,
        mTodayDateTextColor,
        mTodayDateBackgroundColor,
        mDayOfWeekTextColor,
        mUnselectedDayTextColor);
  }

  private int getColor(int colorId) {
    return getResources().getColor(colorId);
  }

  public int getBackgroundColor() {
    int color = Color.TRANSPARENT;
    Drawable background = getBackground();
    if (background instanceof ColorDrawable)
      color = ((ColorDrawable) background).getColor();
    return color;
  }

  @Override
  public boolean post(Runnable action) {
    return rvDays.post(action);
  }

  @Override
  public void onStopDraggingPicker() {
    if (vHover.getVisibility() == VISIBLE)
      vHover.setVisibility(INVISIBLE);
  }

  @Override
  public void onDraggingPicker() {
    if (vHover.getVisibility() == INVISIBLE)
      vHover.setVisibility(VISIBLE);
  }

  @Override
  public void onDateSelected(Day item) {
    tvMonth.setText(item.getMonth(mMonthPattern));
    if (showTodayButton)
      tvToday.setVisibility(item.isToday() ? INVISIBLE : VISIBLE);
    dateChangedSubject.onNext(item.getDate());
  }

  public Observable<LocalDate> getDateChangedObservable() {
    return dateChangedSubject;
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

  public HorizontalPicker setDateSelectedColor(@ColorInt int color) {
    mDateSelectedColor = color;
    return this;
  }

  public HorizontalPicker setDateSelectedTextColor(@ColorInt int color) {
    mDateSelectedTextColor = color;
    return this;
  }

  public HorizontalPicker setMonthAndYearTextColor(@ColorInt int color) {
    mMonthAndYearTextColor = color;
    return this;
  }

  public HorizontalPicker setTodayButtonTextColor(@ColorInt int color) {
    mTodayButtonTextColor = color;
    return this;
  }

  public HorizontalPicker showTodayButton(boolean show) {
    showTodayButton = show;
    return this;
  }

  public HorizontalPicker setTodayDateTextColor(int color) {
    mTodayDateTextColor = color;
    return this;
  }

  public HorizontalPicker setTodayDateBackgroundColor(@ColorInt int color) {
    mTodayDateBackgroundColor = color;
    return this;
  }

  public HorizontalPicker setDayOfWeekTextColor(@ColorInt int color) {
    mDayOfWeekTextColor = color;
    return this;
  }

  public HorizontalPicker setUnselectedDayTextColor(@ColorInt int color) {
    mUnselectedDayTextColor = color;
    return this;
  }

  public HorizontalPicker setMonthPattern(String pattern) {
    mMonthPattern = pattern;
    return this;
  }

  public HorizontalPicker setScrollToSelectedPosition(boolean scrollToSelectedPosition) {
    this.scrollToSelectedPosition = scrollToSelectedPosition;
    return this;
  }
}