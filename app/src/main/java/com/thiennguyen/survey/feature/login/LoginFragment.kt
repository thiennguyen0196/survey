package com.thiennguyen.survey.feature.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    }

    override fun setupViewModel() {
        super.setupViewModel()


    }
}