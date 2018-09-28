package com.github.jhonnyx2012.horizontalpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * Created by Jhonny Barrios on 22/02/2017.
 */

public class HorizontalPickerRecyclerView extends RecyclerView implements OnItemClickedListener, View.OnClickListener {

  private final static int OFFSET = 3;
  private HorizontalPickerAdapter adapter;
  private LinearLayoutManager layoutManager;
  private float itemWidth;
  private HorizontalPickerListener listener;
  private boolean scrollToSelectedPosition;

  public HorizontalPickerRecyclerView(Context context) {
    super(context);
  }

  public HorizontalPickerRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public HorizontalPickerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void init(Context context,
                   final int daysToPlus,
                   final int initialOffset,
                   final boolean scrollToSelectedPosition,
                   final int mBackgroundColor,
                   final int mDateSelectedColor,
                   final int mDateSelectedTextColor,
                   final int mTodayDateTextColor,
                   final int mTodayDateBackgroundColor,
                   final int mDayOfWeekTextColor,
                   final int mUnselectedDayTextColor) {
    this.scrollToSelectedPosition = scrollToSelectedPosition;
    layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    setLayoutManager(layoutManager);
    post(new Runnable() {
      @Override
      public void run() {
        itemWidth = getMeasuredWidth() / 7;
        adapter = new HorizontalPickerAdapter((int) itemWidth, HorizontalPickerRecyclerView.this, getContext(), daysToPlus, initialOffset, mBackgroundColor, mDateSelectedColor, mDateSelectedTextColor, mTodayDateTextColor,
            mTodayDateBackgroundColor,
            mDayOfWeekTextColor,
            mUnselectedDayTextColor,
            OFFSET);
        setAdapter(adapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(HorizontalPickerRecyclerView.this);
        removeOnScrollListener(onScrollListener);
        addOnScrollListener(onScrollListener);
      }
    });
  }

  private OnScrollListener onScrollListener = new OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      switch (newState) {
        case RecyclerView.SCROLL_STATE_IDLE:
          listener.onStopDraggingPicker();
          int position = (int) ((computeHorizontalScrollOffset() / itemWidth) + 3.5);
          if (position != -1 && position != adapter.getSelectedPosition()) {
            selectItem(position);
          }
          break;
        case SCROLL_STATE_DRAGGING:
          listener.onDraggingPicker();
          break;
      }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
    }
  };

  private void selectItem(int position) {
    if (position == NO_POSITION) {
      return;
    }
    if (position != adapter.getSelectedPosition()) {
      int unselectPosition = adapter.getSelectedPosition();
      adapter.setSelectedPosition(position);
      adapter.notifyItemChanged(unselectPosition);
      adapter.notifyItemChanged(position);
      listener.onDateSelected(adapter.getItem(position));
    }
  }

  public void setListener(HorizontalPickerListener listener) {
    this.listener = listener;
  }

  @Override
  public void onClickView(View v, int adapterPosition) {
    if (adapterPosition != adapter.getSelectedPosition()
        && adapter.getItemViewType(adapterPosition) != R.layout.item_placeholder) {
      selectItem(adapterPosition);
      if (scrollToSelectedPosition) {
        smoothScrollToPosition(adapterPosition);
      }
    }
  }

  @Override
  public void onClick(View v) {

  }

  @Override
  public void smoothScrollToPosition(int position) {
    final RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(getContext());
    smoothScroller.setTargetPosition(position);
    post(new Runnable() {
      @Override
      public void run() {
        layoutManager.startSmoothScroll(smoothScroller);
      }
    });
  }

  public void setDate(LocalDate date) {
    Day firstDay = adapter.getItem(OFFSET);
    final int offset = (int) ChronoUnit.DAYS.between(firstDay.getDate(), date);
    selectItem(offset + OFFSET);
    ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(offset, 0);
  }

  private static class CenterSmoothScroller extends LinearSmoothScroller {

    CenterSmoothScroller(Context context) {
      super(context);
    }

    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
      return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
    }
  }
}
