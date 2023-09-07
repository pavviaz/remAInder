package com.svyatocheck.remainder.presentation.schedule.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleListFragment

class SchedulePagerAdapter(
    fragment: Fragment,
    private val length: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = length

    override fun createFragment(position: Int): Fragment =
        ScheduleListFragment.newInstance(position)

}