package com.thiennguyen.survey.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel: HomeViewModel by viewModels()

    private val pagerAdapter by lazy { PagerAdapter() }

    override fun setupUI() {
        super.setupUI()

        binding.btnStartSurvey.setOnClickListener {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
        }
        binding.viewPager.setPageTransformer(FadePageTransformer())
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
            // Do nothing
        }.attach()
    }

    override fun setupViewModel() {
        super.setupViewModel()

    }

    override fun showLoading() {
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.swipeRefreshLayout.visibility = View.GONE
        binding.tvDate.visibility = View.GONE
        binding.tvToday.visibility = View.GONE
        binding.ivAvatar.visibility = View.GONE
        binding.tabLayout.visibility = View.GONE
        binding.btnStartSurvey.visibility = View.GONE
        binding.shimmerLayout.startShimmer()
    }

    override fun hideLoading() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.swipeRefreshLayout.visibility = View.VISIBLE
        binding.tvDate.visibility = View.VISIBLE
        binding.tvToday.visibility = View.VISIBLE
        binding.ivAvatar.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
        binding.btnStartSurvey.visibility = View.VISIBLE
    }
}