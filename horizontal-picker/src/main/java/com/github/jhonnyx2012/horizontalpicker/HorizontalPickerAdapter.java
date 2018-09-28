package com.github.jhonnyx2012.horizontalpicker;


import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

/**
 * Created by jhonn on 22/02/2017.
 */

public class HorizontalPickerAdapter extends RecyclerView.Adapter<HorizontalPickerAdapter.BaseViewHolder> {

  private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
  private final int mBackgroundColor;
  private final int mDateSelectedTextColor;
  private final int mDateSelectedColor;
  private final int mTodayDateTextColor;
  private final int mTodayDateBackgroundColor;
  private final int mDayOfWeekTextColor;
  private final int mUnselectedDayTextColor;
  private int itemWidth;
  private final OnItemClickedListener listener;
  private ArrayList<Day> items;
  private int selectedPosition = RecyclerView.NO_POSITION;
  private final int dummyDaysOffset;

  public HorizontalPickerAdapter(int itemWidth, OnItemClickedListener listener, Context context, int daysToCreate, int offset, int mBackgroundColor, int mDateSelectedColor, int mDateSelectedTextColor, int mTodayDateTextColor, int mTodayDateBackgroundColor, int mDayOfWeekTextColor, int mUnselectedDayTextColor, int dummyDaysOffset) {
    items = new ArrayList<>();
    this.itemWidth = itemWidth;
    this.listener = listener;
    generateDays(daysToCreate, LocalDate.now().minusDays(offset));
    this.mBackgroundColor = mBackgroundColor;
    this.mDateSelectedTextColor = mDateSelectedTextColor;
    this.mDateSelectedColor = mDateSelectedColor;
    this.mTodayDateTextColor = mTodayDateTextColor;
    this.mTodayDateBackgroundColor = mTodayDateBackgroundColor;
    this.mDayOfWeekTextColor = mDayOfWeekTextColor;
    this.mUnselectedDayTextColor = mUnselectedDayTextColor;
    this.dummyDaysOffset = dummyDaysOffset;
  }

  private void generateDays(int n, LocalDate initialDate) {
    for (int i = 0; i < dummyDaysOffset; i++) {
      items.add(new Day.Placeholder());
    }
    int days = 0;
    while (days < n) {
      items.add(new Day(initialDate.plusDays(days++)));
    }
    for (int i = 0; i < dummyDaysOffset; i++) {
      items.add(new Day.Placeholder());
    }
  }


  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    BaseViewHolder holder;
    if (viewType == R.layout.item_day) {
      holder = new DayViewHolder(
          LayoutInflater.from(parent.getContext())
              .inflate(viewType, parent, false));
    } else {
      holder = new PlaceholderViewHolder(
          LayoutInflater.from(parent.getContext())
              .inflate(viewType, parent, false));
    }
    return holder;
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  private Drawable getDaySelectedBackground(View view) {
    Drawable drawable = view.getResources().getDrawable(R.drawable.background_day_selected);
    DrawableCompat.setTint(drawable, mDateSelectedColor);
    return drawable;
  }

  private Drawable getDayTodayBackground(View view) {
    Drawable drawable = view.getResources().getDrawable(R.drawable.background_day_today);
    if (mTodayDateBackgroundColor != -1)
      DrawableCompat.setTint(drawable, mTodayDateBackgroundColor);
    return drawable;
  }

  public Day getItem(int position) {
    return items.get(position);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (position < dummyDaysOffset || position >= getItemCount() - dummyDaysOffset) {
      return R.layout.item_placeholder;
    } else {
      return R.layout.item_day;
    }
  }

  public int getSelectedPosition() {
    return selectedPosition;
  }

  public void setSelectedPosition(int selectedPosition) {
    this.selectedPosition = selectedPosition;
  }

  public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
      super(itemView);
    }

    abstract void bind(Day day);
  }

  public class DayViewHolder extends BaseViewHolder implements View.OnClickListener {
    TextView tvDay, tvWeekDay;

    public DayViewHolder(View itemView) {
      super(itemView);
      tvDay = (TextView) itemView.findViewById(R.id.tvDay);
      tvDay.setWidth(itemWidth);
      tvWeekDay = (TextView) itemView.findViewById(R.id.tvWeekDay);
      itemView.setOnClickListener(this);
    }

    @Override
    void bind(Day item) {
      tvDay.setText(item.getDay());
      tvWeekDay.setText(item.getWeekDay());
      tvWeekDay.setTextColor(mDayOfWeekTextColor);
      if (selectedPosition == getAdapterPosition()) {
        tvDay.setBackgroundDrawable(getDaySelectedBackground(itemView));
        tvDay.setTextColor(mDateSelectedTextColor);
      } else if (item.isToday()) {
        tvDay.setBackgroundDrawable(getDayTodayBackground(itemView));
        tvDay.setTextColor(mTodayDateTextColor);
      } else {
        tvDay.setBackgroundColor(mBackgroundColor);
        tvDay.setTextColor(mUnselectedDayTextColor);
      }
    }

    @Override
    public void onClick(View v) {
      listener.onClickView(v, getAdapterPosition());
    }
  }

  public class PlaceholderViewHolder extends BaseViewHolder {

    public PlaceholderViewHolder(View itemView) {
      super(itemView);
      itemView.setMinimumWidth(itemWidth);
    }

    @Override
    void bind(Day day) {

    }
  }
}