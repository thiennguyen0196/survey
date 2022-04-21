package com.thiennguyen.survey.feature.forgot

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.thiennguyen.survey.R
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.databinding.FragmentForgotBinding
import com.thiennguyen.survey.feature.view.TopSnackBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : BaseFragment<ForgotViewModel, FragmentForgotBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotBinding
        get() = FragmentForgotBinding::inflate

    override val viewModel: ForgotViewModel by viewModels()

    override fun setupUI() {
        super.setupUI()

        binding.btnReset.setOnClickListener {
            viewModel.resetPassword(
                email = binding.etEmail.text.toString()
            )
        }
        binding.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.onResetPasswordChange.observe(viewLifecycleOwner) {
            when (it) {
                is ForgotViewModel.ResetPasswordState.EmailInvalid -> {
                    showSnackErrorMessage(R.string.message_email_invalid)
                }
                is ForgotViewModel.ResetPasswordState.Success -> {
                    showTopSnackBar(
                        title = getString(R.string.title_check_email),
                        message = it.message.ifBlank {
                            getString(R.string.message_check_email)
                        }
                    )
                }
            }
        }
    }

    override fun showLoading() {
        binding.btnReset.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.btnReset.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.pbLoading.visibility = View.GONE
    }

    private fun showTopSnackBar(title: String, message: String) {
        val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
        val layout = snackBar.view as? SnackbarLayout

        // Hide default snack bar's message text view
        val textView = layout?.findViewById<View>(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.visibility = View.INVISIBLE

        // Make custom view for snack bar
        val snackView = TopSnackBarView(requireContext()).apply {
            tvTitle.text = title
            tvMessage.text = message
        }

        // Customize snack bar layout
        layout?.setBackgroundColor(Color.TRANSPARENT)
        layout?.setPadding(0, 0, 0, 0)
        layout?.addView(snackView, 0)
        layout?.layoutParams = (layout?.layoutParams as? FrameLayout.LayoutParams)?.also {
            it.gravity = Gravity.TOP
            it.setMargins(
                it.leftMargin,
                resources.getDimensionPixelSize(R.dimen.space_48),
                it.rightMargin,
                it.bottomMargin
            )
        }
        snackBar.show()
    }
}