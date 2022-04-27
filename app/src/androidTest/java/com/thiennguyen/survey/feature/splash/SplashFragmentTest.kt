package com.thiennguyen.survey.feature.splash

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thiennguyen.survey.Constants
import com.thiennguyen.survey.R
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.utils.launchFragmentInHiltContainer
import com.thiennguyen.survey.utils.withDrawable
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SplashFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    private val mockNavController: NavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun validateUIComponentsExistence() {
        `when`(authenticationRepository.isLoggedIn()).thenReturn(Observable.just(true))
        doNothing().`when`(mockNavController).navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())

        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        onView(ViewMatchers.withId(R.id.ivBackground))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(withDrawable(R.drawable.im_background)))

        onView(ViewMatchers.withId(R.id.ivLogo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(withDrawable(R.drawable.ic_logo_white)))
    }

    @Test
    fun validateNavigateToHome_whenLoggedIn() {
        `when`(authenticationRepository.isLoggedIn()).thenReturn(Observable.just(true))
        doNothing().`when`(mockNavController).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        // Wait for animation to finish
        Thread.sleep(Constants.DelayTime.ANIMATION_FADE)

        verify(mockNavController, times(1)).navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }

    @Test
    fun validateNavigateToLogin_whenNotLoggedIn() {
        `when`(authenticationRepository.isLoggedIn()).thenReturn(Observable.just(false))
        doNothing().`when`(mockNavController).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        // Wait for animation to finish
        Thread.sleep(Constants.DelayTime.ANIMATION_FADE)

        verify(mockNavController, times(1)).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }
}
