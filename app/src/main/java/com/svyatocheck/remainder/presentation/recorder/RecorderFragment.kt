package com.svyatocheck.remainder.presentation.recorder

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.FragmentEditTaskBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


// constant for storing audio permission
const val REQUEST_PERMISSION_CODE = 200

class FragmentRecorder : Fragment(R.layout.fragment_edit_task) {

    // ui binding
    private val binding by viewBinding(FragmentEditTaskBinding::bind)

    // primary view-model
    private val recorderViewModel: RecorderViewModel by viewModel()

    // permissions
    private var permissions = arrayOf(RECORD_AUDIO)
    private var permissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check if we have the permission to record an audio
        permissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // we don't have the permission yet
        if (!permissionGranted)
            requestPermission()

        getRequestedCode()

        initView()
    }

    private fun initView() {
        with(binding) {
            sendAudioBtn.setOnClickListener {
                recorderViewModel.stopRecording()
            }

            backArrowIv.setOnClickListener {
                recorderViewModel.stopRecording()
                parentFragmentManager.popBackStack()
            }

            recorderViewModel.networkingStatus.observe(viewLifecycleOwner) {
                when (it) {
                    RequestStateStatus.LOADING -> {
                        binding.descriptionTv.text =
                            getString(R.string.string_sending_audio_message)
                        binding.loadingPlaceholder.visibility = View.VISIBLE
                        binding.sendAudioBtn.visibility = View.GONE
                    }

                    RequestStateStatus.DONE -> {
                        parentFragmentManager.popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Sent!", Toast.LENGTH_SHORT
                        ).show()
                    }

                    RequestStateStatus.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Unable to send audio this time :(", Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

            descriptionTv.text = getString(R.string.string_init_recorder_status)
        }
        startRecording()
    }


    private fun startRecording() {
        // we don't have the permission yet
        if (!permissionGranted) {
            requestPermission()
            return
        }
        binding.descriptionTv.text = getString(R.string.string_speak_status_message)
        recorderViewModel.startRecording()
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            REQUEST_PERMISSION_CODE
        )
    }

    private fun getRequestedCode() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionGranted = isGranted
        }
    }

}