package com.thiennguyen.survey.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.thiennguyen.survey.R

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment(), OnLoadingSupporting {

    lateinit var binding: VB

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract val viewModel: VM

    protected val navController: NavController?
        get() {
            return activity?.let {
                try {
                    Navigation.findNavController(it, R.id.nav_host_fragment)
                } catch (e: IllegalStateException) {
                    null
                }
            }
        }

    open fun setupUI() = Unit

    open fun setupViewModel() {
        viewModel.onLoadingChange.observe(viewLifecycleOwner) {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        }
        viewModel.onErrorChange.observe(viewLifecycleOwner) {
            showSnackErrorMessage(R.string.message_error)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
    }

    protected fun showSnackErrorMessage(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
