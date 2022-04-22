package com.thiennguyen.survey.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.thiennguyen.survey.base.BaseFragment
import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel: HomeViewModel by viewModels()

    private val pagerAdapter by lazy { PagerAdapter() }

    private val onPageChangeCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == pagerAdapter.itemCount - 1) {
                    this@HomeFragment.viewModel.getSurveyList()
                }
            }
        }
    }

    private var isRefresh: Boolean = false

    override fun setupUI() {
        super.setupUI()

        binding.tvDate.text = SimpleDateFormat(Constants.DateFormat.DAY_OF_WEEK, Locale.getDefault()).format(Date())
        binding.btnStartSurvey.setOnClickListener {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            viewModel.getSurveyList(isRefresh = true)
        }
        binding.viewPager.setPageTransformer(FadePageTransformer())
        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
            // Do nothing
        }.attach()
    }

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.onSurveyListChange.observe(viewLifecycleOwner) {
            if (isRefresh || pagerAdapter.itemCount == 0) {
                binding.viewPager.currentItem = 0
                pagerAdapter.submitList(it)
                isRefresh = false
            } else {
                pagerAdapter.addAll(it)
            }
        }
        viewModel.onUserProfileAvatarChange.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it)
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }

    override fun showLoading() {
        if (!binding.swipeRefreshLayout.isRefreshing && pagerAdapter.itemCount == 0) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.swipeRefreshLayout.visibility = View.GONE
            binding.tvDate.visibility = View.GONE
            binding.tvToday.visibility = View.GONE
            binding.ivAvatar.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.btnStartSurvey.visibility = View.GONE
            binding.shimmerLayout.startShimmer()
        }
    }

    override fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.swipeRefreshLayout.visibility = View.VISIBLE
        binding.tvDate.visibility = View.VISIBLE
        binding.tvToday.visibility = View.VISIBLE
        binding.ivAvatar.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
        binding.btnStartSurvey.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        super.onDestroy()
    }
}