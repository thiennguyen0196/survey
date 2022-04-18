package com.thiennguyen.survey.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<VM : BaseViewModel, VB: ViewBinding> : Fragment() {

    lateinit var binding: ViewBinding
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract val viewModel: VM

    open fun setupUI() = Unit
    open fun setupViewModel() = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
    }
}
