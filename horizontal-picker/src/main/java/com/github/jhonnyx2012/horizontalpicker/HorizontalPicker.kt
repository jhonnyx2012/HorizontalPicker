package com.github.jhonnyx2012.horizontalpicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import org.joda.time.DateTime

/**
 * Created by Jhonny Barrios on 22/02/2017.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")

class HorizontalPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), HorizontalPickerListener {

    private lateinit var rvDays: HorizontalPickerRecyclerView
    private lateinit var vHover: View
    private lateinit var tvMonth: TextView
    private lateinit var tvToday: TextView

    private var listener: DatePickerListener? = null
    private var monthListener: OnTouchListener? = null

    var days = NO_SET; private set
    var offset = NO_SET; private set

    @ColorInt private var mDateSelectedColor = -1
    @ColorInt private var mDateSelectedTextColor = -1
    @ColorInt private var mMonthAndYearTextColor = -1
    @ColorInt private var mTodayButtonTextColor = -1
    @ColorInt private var mTodayDateTextColor = -1
    @ColorInt private var mTodayDateBackgroundColor = -1
    @ColorInt private var mDayOfWeekTextColor = -1
    @ColorInt private var mUnselectedDayTextColor = -1
    private var mMonthPattern = ""
    private var showTodayButton = true

    fun setListener(listener: DatePickerListener): HorizontalPicker {
        this.listener = listener
        return this
    }

    fun setMonthListener(listener: OnTouchListener): HorizontalPicker {
        monthListener = listener
        return this
    }

    fun setDate(date: DateTime) {
        rvDays.post { rvDays.setDate(date) }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initialize() {
        inflate(context, R.layout.horizontal_picker, this)
        rvDays = findViewById(R.id.rvDays)
        val finalDays = if (days == NO_SET) DEFAULT_DAYS_TO_PLUS else days
        val finalOffset = if (offset == NO_SET) DEFAULT_INITIAL_OFFSET else offset
        vHover = findViewById(R.id.vHover)
        tvMonth = findViewById(R.id.tvMonth)
        if (monthListener != null) {
            tvMonth.isClickable = true
            tvMonth.setOnTouchListener(monthListener)
        }
        tvToday = findViewById(R.id.tvToday)
        rvDays.setListener(this)
        tvToday.setOnClickListener(rvDays)
        tvMonth.setTextColor(
            if (mMonthAndYearTextColor != -1) mMonthAndYearTextColor else getColor(
                R.color.primaryTextColor
            )
        )

        tvToday.visibility = if (showTodayButton) VISIBLE else INVISIBLE
        tvToday.setTextColor(if (mTodayButtonTextColor != -1) mTodayButtonTextColor else getColor(R.color.colorPrimary))

        val mBackgroundColor = backgroundColor
        setBackgroundColor(if (mBackgroundColor != Color.TRANSPARENT) mBackgroundColor else Color.WHITE)

        mDateSelectedColor =
            if (mDateSelectedColor == -1) getColor(R.color.colorPrimary) else mDateSelectedColor
        mTodayDateTextColor =
            if (mTodayDateTextColor == -1) getColor(R.color.primaryTextColor) else mTodayDateTextColor
        mDayOfWeekTextColor =
            if (mDayOfWeekTextColor == -1) getColor(R.color.secundaryTextColor) else mDayOfWeekTextColor
        mUnselectedDayTextColor =
            if (mUnselectedDayTextColor == -1) getColor(R.color.primaryTextColor) else mUnselectedDayTextColor

        rvDays.initialize(
            finalDays,
            finalOffset,
            mBackgroundColor,
            mDateSelectedColor,
            mDateSelectedTextColor,
            mTodayDateTextColor,
            mTodayDateBackgroundColor,
            mDayOfWeekTextColor,
            mUnselectedDayTextColor
        )
    }

    private fun getColor(@ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    val backgroundColor: Int
        get() {
            var color = Color.TRANSPARENT
            val background = background
            if (background is ColorDrawable) color = background.color
            return color
        }

    override fun post(action: Runnable): Boolean {
        return rvDays.post(action)
    }

    override fun onStopDraggingPicker() {
        if (vHover.visibility == VISIBLE) {
            vHover.visibility = INVISIBLE
        }
    }

    override fun onDraggingPicker() {
        if (vHover.visibility == INVISIBLE) {
            vHover.visibility = VISIBLE
        }
    }

    override fun onDateSelected(item: Day) {
        tvMonth.text = item.getMonth(mMonthPattern)
        if (showTodayButton) {
            tvToday.visibility = if (item.isToday) INVISIBLE else VISIBLE
        }
        listener?.onDateSelected(item.getDate())
    }

    fun setDays(days: Int): HorizontalPicker {
        this.days = days
        return this
    }

    fun setOffset(offset: Int): HorizontalPicker {
        this.offset = offset
        return this
    }

    fun setDateSelectedColor(@ColorInt color: Int): HorizontalPicker {
        mDateSelectedColor = color
        return this
    }

    fun setDateSelectedTextColor(@ColorInt color: Int): HorizontalPicker {
        mDateSelectedTextColor = color
        return this
    }

    fun setMonthAndYearTextColor(@ColorInt color: Int): HorizontalPicker {
        mMonthAndYearTextColor = color
        return this
    }

    fun setTodayButtonTextColor(@ColorInt color: Int): HorizontalPicker {
        mTodayButtonTextColor = color
        return this
    }

    fun showTodayButton(show: Boolean): HorizontalPicker {
        showTodayButton = show
        return this
    }

    fun setTodayDateTextColor(@ColorInt color: Int): HorizontalPicker {
        mTodayDateTextColor = color
        return this
    }

    fun setTodayDateBackgroundColor(@ColorInt color: Int): HorizontalPicker {
        mTodayDateBackgroundColor = color
        return this
    }

    fun setDayOfWeekTextColor(@ColorInt color: Int): HorizontalPicker {
        mDayOfWeekTextColor = color
        return this
    }

    fun setUnselectedDayTextColor(@ColorInt color: Int): HorizontalPicker {
        mUnselectedDayTextColor = color
        return this
    }

    fun setMonthPattern(pattern: String): HorizontalPicker {
        mMonthPattern = pattern
        return this
    }

    companion object {
        private const val DEFAULT_DAYS_TO_PLUS = 120
        private const val DEFAULT_INITIAL_OFFSET = 7
        private const val NO_SET = -1
    }
}