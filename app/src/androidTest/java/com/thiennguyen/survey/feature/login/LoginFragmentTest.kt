package com.thiennguyen.survey.feature.login

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thiennguyen.survey.R
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.Password
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.utils.launchFragmentInHiltContainer
import com.thiennguyen.survey.utils.withDrawable
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.rxjava3.core.Completable
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
class LoginFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    private val mockNavController: NavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }
    }

    @Test
    fun validateUIComponentsExistence() {
        onView(withId(R.id.ivBackground))
            .check(matches(isDisplayed()))
            .check(matches(withDrawable(R.drawable.im_background_blur)))

        onView(withId(R.id.ivLogo))
            .check(matches(isDisplayed()))
            .check(matches(withDrawable(R.drawable.ic_logo_white)))

        onView(withId(R.id.etEmail))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.hint_email)))

        onView(withId(R.id.etPassword)).check(matches(isDisplayed()))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.hint_password)))

        onView(withId(R.id.tvForgot)).check(matches(isDisplayed()))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.btnForgotPassword)))

        onView(withId(R.id.btnLogin))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.btn_login)))
    }

    @Test
    fun validateShowSnackBarError_whenEmailIsBlank() {
        onView(withId(R.id.btnLogin))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_email_invalid)))
    }

    @Test
    fun validateShowSnackBarError_whenEmailIsInvalid() {
        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail"))
        onView(withId(R.id.btnLogin))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_email_invalid)))
    }

    @Test
    fun validateShowSnackBarError_whenPasswordIsbBlank() {
        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail.com"))
        onView(withId(R.id.btnLogin))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_password_invalid)))
    }

    @Test
    fun validateShowSnackBarError_whenSubmitLoginError() {
        `when`(authenticationRepository.submitLogin(Email("abc@gmail.com"), Password("abc123")))
            .thenReturn(Completable.error(NullPointerException()))

        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail.com"))
        onView(withId(R.id.etPassword))
            .perform(ViewActions.typeText("abc123"))
        onView(withId(R.id.btnLogin))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_error)))
    }

    @Test
    fun validateNavigateToHome_whenSubmitLoginSuccess() {
        `when`(authenticationRepository.submitLogin(Email("abc@gmail.com"), Password("abc123")))
            .thenReturn(Completable.complete())
        doNothing().`when`(mockNavController).navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail.com"))
        onView(withId(R.id.etPassword))
            .perform(ViewActions.typeText("abc123"))
        onView(withId(R.id.btnLogin))
            .perform(ViewActions.click())

        verify(mockNavController, times(1)).navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    @Test
    fun validateNavigateToForgot_whenClickingForgotButton() {
        doNothing().`when`(mockNavController).navigate(LoginFragmentDirections.actionLoginFragmentToForgotFragment())

        onView(withId(R.id.tvForgot))
            .perform(ViewActions.click())

        verify(mockNavController, times(1)).navigate(LoginFragmentDirections.actionLoginFragmentToForgotFragment())
    }
}
