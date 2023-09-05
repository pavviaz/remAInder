package com.svyatocheck.remainder.presentation.schedule.adapters

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.ListItemCalendarDateBinding
import com.svyatocheck.remainder.presentation.models.CalendarWeekDay


class CalendarClassicAdapter(val listener: onClickListener) :
    RecyclerView.Adapter<CalendarClassicAdapter.CalendarViewHolder>() {

    private var previousPosition = -1
    private var selectedPosition = -1

    var list = ArrayList<CalendarWeekDay>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    fun setSelectedItem(position: Int) {
        previousPosition = selectedPosition
        selectedPosition = position

        if (previousPosition >= 0) {
            list[previousPosition].isSelected = false
            notifyItemChanged(previousPosition)
        }

        list[selectedPosition].isSelected = true
        notifyItemChanged(selectedPosition)
    }

    inner class CalendarViewHolder(private val binding: ListItemCalendarDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarDateModel: CalendarWeekDay, position: Int) {
            binding.calendarDay.text = calendarDateModel.calendarDay
            binding.calendarDate.text = calendarDateModel.calendarDate

            binding.root.setOnClickListener {
                setSelectedItem(position)
                listener.onClick(calendarDateModel, position)
            }

            when (calendarDateModel.isSelected) {
                true -> {
                    binding.calendarDate.setTextColor(Color.WHITE)
                    binding.calendarDay.setTextColor(Color.WHITE)
                    binding.linearDayRoot.setBackgroundResource(R.drawable.day_background_selected)
                }

                else -> {
                    if (isNightMode(itemView.context)) {
                        binding.calendarDate.setTextColor(Color.WHITE)
                    } else {
                        binding.calendarDate.setTextColor(Color.BLACK)
                    }

                    binding.calendarDay.setTextColor(Color.GRAY)
                    binding.linearDayRoot.setBackgroundResource(R.drawable.day_background_default)
                }
            }
        }

        fun isNightMode(context: Context): Boolean {
            val nightModeFlags =
                context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCalendarDateBinding.inflate(inflater, parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    interface onClickListener {
        fun onClick(calendarDateModel: CalendarWeekDay, position: Int)
    }
}