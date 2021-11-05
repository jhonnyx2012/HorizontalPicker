package com.github.jhonnyx2012.horizontalpicker

import android.app.AlarmManager
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import java.util.*

/**
 * Created by jhonny on 22/02/2017.
 */
class HorizontalPickerAdapter(
    private val itemWidth: Int,
    private val listener: OnItemClickedListener,
    daysToCreate: Int,
    offset: Int,
    @ColorInt private val mBackgroundColor: Int,
    @ColorInt private val mDateSelectedColor: Int,
    @ColorInt private val mDateSelectedTextColor: Int,
    @ColorInt private val mTodayDateTextColor: Int,
    @ColorInt private val mTodayDateBackgroundColor: Int,
    @ColorInt private val mDayOfWeekTextColor: Int,
    @ColorInt private val mUnselectedDayTextColor: Int,
) : RecyclerView.Adapter<HorizontalPickerAdapter.ViewHolder>() {

    private val items: ArrayList<Day> = ArrayList()

    init {
        generateDays(daysToCreate, DateTime().minusDays(offset).millis)
    }

    private fun generateDays(n: Int, initialDate: Long) {
        var i = 0
        while (i < n) {
            val actualDate = DateTime(initialDate + DAY_MILLIS * i++)
            items.add(Day(actualDate))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvDay.text = item.day
        holder.tvWeekDay.text = item.weekDay
        holder.tvWeekDay.setTextColor(mDayOfWeekTextColor)
        when {
            item.isSelected -> {
                holder.tvDay.background = getDaySelectedBackground(holder.itemView)
                holder.tvDay.setTextColor(mDateSelectedTextColor)
            }
            item.isToday -> {
                holder.tvDay.background = getDayTodayBackground(holder.itemView)
                holder.tvDay.setTextColor(mTodayDateTextColor)
            }
            else -> {
                holder.tvDay.setBackgroundColor(mBackgroundColor)
                holder.tvDay.setTextColor(mUnselectedDayTextColor)
            }
        }
    }

    private fun getDaySelectedBackground(view: View): Drawable {
        val drawable = ContextCompat.getDrawable(view.context, R.drawable.background_day_selected)!!
        DrawableCompat.setTint(drawable, mDateSelectedColor)
        return drawable
    }

    private fun getDayTodayBackground(view: View): Drawable {
        val drawable = ContextCompat.getDrawable(view.context, R.drawable.background_day_today)!!
        if (mTodayDateBackgroundColor != -1) {
            DrawableCompat.setTint(drawable, mTodayDateBackgroundColor)
        }
        return drawable
    }

    fun getItem(position: Int) = items[position]

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvDay: TextView = itemView.findViewById(R.id.tvDay)
        var tvWeekDay: TextView

        override fun onClick(v: View) {
            listener.onClickView(v, bindingAdapterPosition)
        }

        init {
            tvDay.width = itemWidth
            tvWeekDay = itemView.findViewById(R.id.tvWeekDay)
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        private const val DAY_MILLIS = AlarmManager.INTERVAL_DAY
    }
}