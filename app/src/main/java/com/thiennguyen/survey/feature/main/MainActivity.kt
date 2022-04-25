package com.thiennguyen.survey.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thiennguyen.survey.R
import com.thiennguyen.survey.base.OnBackPressedListener
import com.thiennguyen.survey.base.OnChildViewLayout
import com.thiennguyen.survey.databinding.ActivityMainBinding
import com.thiennguyen.survey.utils.setLightStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navHostFragment: Fragment? by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLightStatusBar()
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull { it !is OnChildViewLayout }
        if (currentFragment is OnBackPressedListener) {
            val result = (currentFragment as OnBackPressedListener).onBackPressed()
            if (result) return
        }

        super.onBackPressed()
    }
}
