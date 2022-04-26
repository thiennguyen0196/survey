package com.thiennguyen.survey.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

abstract class BaseViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxTrampolineSchedulerRule()
    }

    @Before
    open fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    open fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}
