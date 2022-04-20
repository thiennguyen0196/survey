package com.thiennguyen.survey.feature.splash

import android.animation.Animator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.thiennguyen.survey.Constants
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override val viewModel: SplashViewModel by viewModels()

    override fun setupUI() {
        super.setupUI()

        binding.ivLogo.animate()
            .alpha(1f)
            .setDuration(Constants.DelayTime.ANIMATION_FADE)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(p0: Animator?) {
                    binding.ivLogo.alpha = 1f
                    viewModel.checkIsLoggedIn()
                }

                override fun onAnimationStart(p0: Animator?) = Unit
                override fun onAnimationCancel(p0: Animator?) = Unit
                override fun onAnimationRepeat(p0: Animator?) = Unit
            })
    }

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.onIsLoggedInStateChange.observe(viewLifecycleOwner) {
            when (it) {
                SplashViewModel.LoggedInState.LoggedIn -> {
                    navController?.navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }
                SplashViewModel.LoggedInState.NotLoggedIn -> {
                    navController?.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }
            }
        }
    }
}