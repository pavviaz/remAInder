package com.svyatocheck.remainder.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.svyatocheck.remainder.R
import com.svyatocheck.remainder.databinding.FragmentRegistrationBinding
import com.svyatocheck.remainder.presentation.schedule.utills.RequestStateStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    // ui binding
    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    // primary view-model
    private val registrationViewModel: RegistrationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tvRegistration.setOnClickListener {
            with(binding) {
                val email = tvEmail.editText?.text.toString()
                val password = tvPwd.editText?.text.toString()
                val name = tvName.editText?.text.toString()
                if ((email.isNotBlank()) && (password.isNotBlank()) && (name.isNotBlank())) {
                    registrationViewModel.registration(email, password, name)
                }
            }
        }

        registrationViewModel.networkingStatus.observe(viewLifecycleOwner) {
            when (it) {
                RequestStateStatus.DONE -> {
                    findNavController().navigate(R.id.action_registrationFragment_to_scheduleMainFragment)
                }

                RequestStateStatus.LOADING -> {
                    binding.loadingPlaceholder.visibility = View.VISIBLE
                    binding.tvRegistration.isEnabled = false
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка во время регистрации!",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loadingPlaceholder.visibility = View.GONE
                    binding.tvRegistration.isEnabled = true
                }
            }
        }
    }
}