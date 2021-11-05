package com.github.jhonnyx2012.horizontalpicker;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Jhonny Barrios on 22/02/2017.
 *
 */

public class HorizontalPickerRecyclerView extends RecyclerView implements OnItemClickedListener, View.OnClickListener {

    private HorizontalPickerAdapter adapter;
    private int lastPosition;
    private LinearLayoutManager layoutManager;
    private float itemWidth;
    private HorizontalPickerListener listener;
    private int offset;

    public HorizontalPickerRecyclerView(Context context) {
        super(context);
    }

    public HorizontalPickerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalPickerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(Context context, final int daysToPlus, final int initialOffset, final int mBackgroundColor, final int mDateSelectedColor, final int mDateSelectedTextColor, final int mTodayDateTextColor, final int mTodayDateBackgroundColor, final int mDayOfWeekTextColor, final int mUnselectedDayTextColor) {
        this.offset=initialOffset;
        layoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);
        post(new Runnable() {
            @Override
            public void run() {
                itemWidth=getMeasuredWidth()/7;
                adapter=new HorizontalPickerAdapter((int) itemWidth,HorizontalPickerRecyclerView.this, getContext(),daysToPlus,initialOffset,mBackgroundColor,mDateSelectedColor,mDateSelectedTextColor,mTodayDateTextColor,
                        mTodayDateBackgroundColor,
                        mDayOfWeekTextColor,
                        mUnselectedDayTextColor);
                setAdapter(adapter);
                LinearSnapHelper snapHelper=new LinearSnapHelper();
                snapHelper.attachToRecyclerView(HorizontalPickerRecyclerView.this);
                removeOnScrollListener(onScrollListener);
                addOnScrollListener(onScrollListener);
            }
        });
    }

    private OnScrollListener onScrollListener=new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState){
                case RecyclerView.SCROLL_STATE_IDLE:
                    listener.onStopDraggingPicker();
                    int position = (int) ((computeHorizontalScrollOffset()/itemWidth)+3.5);
                    if(position!=-1&&position!=lastPosition)
                    {
                        selectItem(true,position);
                        selectItem(false,lastPosition);
                        lastPosition=position;
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

    private void selectItem(boolean isSelected,int position) {
        adapter.getItem(position).setSelected(isSelected);
        adapter.notifyItemChanged(position);
        if(isSelected)
        {
            listener.onDateSelected(adapter.getItem(position));
        }
    }

    public void setListener(HorizontalPickerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClickView(View v, int adapterPosition) {
        if(adapterPosition!=lastPosition)
        {
            selectItem(true,adapterPosition);
            selectItem(false,lastPosition);
            lastPosition=adapterPosition;
        }
    }

    @Override
    public void onClick(View v) {
        setDate(new DateTime());
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

    public void setDate(DateTime date) {
        DateTime today = new DateTime().withTime(0,0,0,0);
        int difference = Days.daysBetween(date,today).getDays() * (date.getYear() < today.getMillis() ? -1 : 1);
        smoothScrollToPosition(offset+difference);
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
