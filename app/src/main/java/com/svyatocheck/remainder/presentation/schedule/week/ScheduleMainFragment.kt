package com.svyatocheck.remainder.presentation.schedule.week

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.core.Network
import com.svyatocheck.remainder.databinding.FragmentScheduleMainBinding
import com.svyatocheck.remainder.presentation.models.CalendarWeekDay
import com.svyatocheck.remainder.presentation.schedule.adapters.CalendarClassicAdapter
import com.svyatocheck.remainder.presentation.schedule.adapters.SchedulePagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main) {

    // ui binding
    private val binding by viewBinding(FragmentScheduleMainBinding::bind)

    // primary view-model
    private val scheduleViewModel: ScheduleWeekViewModel by viewModel()

    // pager loading animation model
    private val shimmerModel: ScheduleShimmerViewModel by viewModel()

    // days adapters
    private lateinit var calendarAdapter: CalendarClassicAdapter
    private lateinit var schedulePagerAdapter: SchedulePagerAdapter

    // sync lists of days and dates
    private val pageChangeCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                scheduleViewModel.setSelectedPosition(position)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setUpAdapter()
        setupViewModels()

        if (Network.isNetworkAvailable(context)) {
            scheduleViewModel.loadSchedule()
        } else {
            showWarningMessage("Нет подключения к интернету")
        }

        binding.floatingBtn.setOnClickListener{
            findNavController().navigate(
                R.id.action_scheduleMainFragment_to_fragmentRecorder
            )
        }
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewCalendar)
        calendarAdapter = CalendarClassicAdapter(object:CalendarClassicAdapter.onClickListener {
            override fun onClick(calendarDateModel: CalendarWeekDay, position: Int) {
                binding.viewPagerScheduleScreen.setCurrentItem(position, true)
            }
        })

        binding.recyclerViewCalendar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCalendar.adapter = calendarAdapter

        scheduleViewModel.calendarWeekDay.observe(viewLifecycleOwner) {
            calendarAdapter.list = it

            schedulePagerAdapter = SchedulePagerAdapter(this@ScheduleMainFragment, it.size)
            binding.viewPagerScheduleScreen.adapter = schedulePagerAdapter
            binding.viewPagerScheduleScreen.registerOnPageChangeCallback(pageChangeCallback)
        }

        scheduleViewModel.selectedPosition.observe(viewLifecycleOwner){
            calendarAdapter.setSelectedItem(it)

            binding.recyclerViewCalendar.scrollToPosition(it)
            binding.viewPagerScheduleScreen.currentItem = it
        }
    }

    private fun setupViewModels() {
        scheduleViewModel.scheduleLoadingStatus.observe(viewLifecycleOwner){
            when (it) {
                RequestStateStatus.DONE -> {hideSkeletonsProgress()}
                RequestStateStatus.LOADING -> {showSkeletonsProgress()}
                else -> {
                    showWarningMessage("Ошибка во время загрузки!")
                }
            }
        }
    }


    private fun showWarningMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSkeletonsProgress() {
        shimmerModel.sendMessage(true)
    }

    private fun hideSkeletonsProgress() {
        shimmerModel.sendMessage(false)
    }
}