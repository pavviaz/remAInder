package com.application.feature_schedule.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svyatocheck.remainder.databinding.ListItemScheduleBinding
import com.svyatocheck.remainder.presentation.models.ScheduleItem

class ScheduleListAdapter(
) : RecyclerView.Adapter<ScheduleListAdapter.ScheduleItemViewHolder>() {

    var scheduleList : MutableList<ScheduleItem> = mutableListOf()
        set(value){
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        return ScheduleItemViewHolder(
            ListItemScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = scheduleList.size

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(scheduleList[position], position)
    }

    class ScheduleItemViewHolder(private val binding: ListItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleItem, position: Int) {
            binding.itemClassName.text = item.title
            binding.itemClassTime.text = item.date
        }
    }

}