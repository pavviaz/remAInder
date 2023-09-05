package com.svyatocheck.remainder.presentation.schedule.week

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.FragmentSchedulePagerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleListFragment : Fragment(R.layout.fragment_schedule_pager) {

    // ui binding
    private val binding by viewBinding(FragmentSchedulePagerBinding::bind)

    private val shimmerModel: ScheduleShimmerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerModel.flag.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.placeholder.visibility = View.INVISIBLE
                }

                else -> {
                    binding.shimmerLayout.visibility = View.GONE
                    binding.placeholder.visibility = View.VISIBLE
                }
            }
        }

    }

}