package com.thiennguyen.survey.feature.forgot

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
import com.thiennguyen.survey.domain.model.MetaModel
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ForgotFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    private val mockNavController: NavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()

        launchFragmentInHiltContainer<ForgotFragment> {
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

        onView(withId(R.id.ivBack))
            .check(matches(isDisplayed()))
            .check(matches(withDrawable(R.drawable.ic_back_white)))

        onView(withId(R.id.tvReset))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.title_forgot_password)))

        onView(withId(R.id.etEmail))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.hint_email)))

        onView(withId(R.id.btnReset))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.btn_reset)))
    }

    @Test
    fun validateShowSnackBarError_whenEmailIsBlank() {
        onView(withId(R.id.btnReset))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_email_invalid)))
    }

    @Test
    fun validateShowSnackBarError_whenEmailIsInvalid() {
        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail"))
        onView(withId(R.id.btnReset))
            .perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.message_email_invalid)))
    }

    @Test
    fun validateShowSnackBarError_whenResetPasswordError() {
        `when`(authenticationRepository.resetPassword(Email("abc@gmail.com")))
            .thenReturn(Observable.just(MetaModel()))

        onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("abc@gmail.com"))
        onView(withId(R.id.btnReset))
            .perform(ViewActions.click())

        onView(withText(R.string.message_check_email))
            .check(matches(isDisplayed()))
        onView(withText(R.string.title_check_email))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validatePopBackStack_whenClickingBack() {
        `when`(mockNavController.popBackStack()).thenReturn(true)

        onView(withId(R.id.ivBack))
            .perform(ViewActions.click())

        verify(mockNavController, times(1)).popBackStack()
    }
}
