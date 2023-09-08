package com.svyatocheck.remainder.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.FragmentAuthorizationBinding
import com.svyatocheck.remainder.presentation.schedule.utills.RequestStateStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    // ui binding
    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    // primary view-model
    private val recorderViewModel: AuthorizationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
//        binding.tvEmail.editText?.setText("testtt@gmail.com")
//        binding.tvPwd.editText?.setText("123456")
        binding.tvSignIn.setOnClickListener {
            with(binding) {
                val email = tvEmail.editText?.text.toString()
                val password = tvPwd.editText?.text.toString()
                if (email.isNotBlank() && password.isNotBlank()) {
                    recorderViewModel.authorization(email, password)
                }
            }
        }

        binding.tvRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }

        recorderViewModel.networkingStatus.observe(viewLifecycleOwner){
            when (it) {
                RequestStateStatus.DONE -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_scheduleMainFragment)
                }

                RequestStateStatus.LOADING -> {
                    binding.loadingPlaceholder.visibility = View.VISIBLE
                    binding.tvSignIn.isEnabled = false
                    binding.tvRegistration.isEnabled = false
                }

                else -> {
                    Toast.makeText(requireContext(), "Something is wrong...", Toast.LENGTH_SHORT).show()
                    binding.loadingPlaceholder.visibility = View.INVISIBLE
                    binding.tvSignIn.isEnabled = true
                    binding.tvRegistration.isEnabled = true
                }
            }
        }
    }

}