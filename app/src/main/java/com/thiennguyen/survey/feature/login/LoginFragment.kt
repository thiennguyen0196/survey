package com.thiennguyen.survey.feature.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.thiennguyen.survey.R
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override val viewModel: LoginViewModel by viewModels()

    override fun setupUI() {
        super.setupUI()

        binding.btnLogin.setOnClickListener {
            viewModel.submitLogin(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
        binding.tvForgot.setOnClickListener {
            navController?.navigate(LoginFragmentDirections.actionLoginFragmentToForgotFragment())
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.onLoginStateChanged.observe(viewLifecycleOwner) {
            when (it) {
                is LoginViewModel.LoginState.EmailInvalid -> {
                    showSnackErrorMessage(R.string.message_email_invalid)
                }
                is LoginViewModel.LoginState.PasswordInvalid -> {
                    showSnackErrorMessage(R.string.message_password_invalid)
                }
                is LoginViewModel.LoginState.Success -> {
                    navController?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }
        }
    }

    override fun showLoading() {
        binding.btnLogin.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.etPassword.isEnabled = false
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.btnLogin.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.etPassword.isEnabled = true
        binding.pbLoading.visibility = View.GONE
    }
}
