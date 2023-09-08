package com.svyatocheck.remainder.presentation.schedule.week

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.application.feature_schedule.presentation.adapters.ScheduleListAdapter
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.FragmentSchedulePagerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class ScheduleListFragment : Fragment(R.layout.fragment_schedule_pager) {

    // ui binding
    private val binding by viewBinding(FragmentSchedulePagerBinding::bind)

    private val shimmerModel: ScheduleShimmerViewModel by viewModel()

    // get schedule for pager
    private val calendarViewModel: CalendarViewModel by viewModel()

    private lateinit var tasksAdapter: ScheduleListAdapter

    private var fragmentPosition by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPosition = arguments?.getInt(ARG_COLUMN_POSITION) ?: 0
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            tasksAdapter = ScheduleListAdapter()
            fullDayScheduleRecycler.adapter = tasksAdapter
            fullDayScheduleRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        shimmerModel.flag.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.placeholder.visibility = View.INVISIBLE
                    binding.fullDayScheduleRecycler.visibility = View.INVISIBLE
                }

                else -> {
                    binding.shimmerLayout.visibility = View.GONE
                }
            }
        }

        calendarViewModel.dailyTasks.observe(viewLifecycleOwner) {
            if (fragmentPosition == calendarViewModel.position.value) {
                if (it.isNotEmpty()) {
                    tasksAdapter.scheduleList = it.toMutableList()
                    binding.placeholder.visibility = View.GONE
                    binding.fullDayScheduleRecycler.visibility = View.VISIBLE
                } else {
                    binding.placeholder.visibility = View.VISIBLE
                    binding.fullDayScheduleRecycler.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {

        private const val ARG_COLUMN_POSITION = "POSITION"
        fun newInstance(position: Int) = ScheduleListFragment().apply {
            Log.d("Position", "$position")
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_POSITION, position)
            }
        }
    }
}