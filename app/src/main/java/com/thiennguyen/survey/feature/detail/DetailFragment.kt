package com.thiennguyen.survey.feature.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<BaseViewModel, FragmentDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override val viewModel: BaseViewModel by viewModels()

    override fun setupUI() {
        super.setupUI()

        binding.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }
}