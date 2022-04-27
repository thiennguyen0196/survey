package com.thiennguyen.survey.feature.home

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thiennguyen.survey.R
import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyAttributesModel
import com.thiennguyen.survey.domain.model.SurveyDataModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.model.SurveyQuestionsModel
import com.thiennguyen.survey.domain.model.SurveyRelationshipModel
import com.thiennguyen.survey.domain.model.UserModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import com.thiennguyen.survey.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var surveyRepository: SurveyRepository

    private val mockNavController: NavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun validateUIComponentsExistence() {
        `when`(surveyRepository.getSurveyList(anyInt(), anyInt()))
            .thenReturn(Observable.just(Pair(MetaModel(), listOf())))
        `when`(surveyRepository.getUserProfile())
            .thenReturn(Observable.just(UserModel()))

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        onView(withId(R.id.tvDate))
            .check(matches(isDisplayed()))
            .check(matches(withText(SimpleDateFormat(Constants.DateFormat.DAY_OF_WEEK, Locale.getDefault()).format(Date()))))

        onView(withId(R.id.tvToday))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.text_today)))

        onView(withId(R.id.ivAvatar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.swipeRefreshLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tabLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.viewPager))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnStartSurvey))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validateViewPagerDisplayedCorrectly_whenResponseReturnSurveyList() {
        val surveyModel1 = SurveyModel(
            id = UUID.randomUUID().toString(),
            type = "type_testing_1",
            attributes = SurveyAttributesModel(title = "title_testing_1"),
            relationships = SurveyRelationshipModel(
                questions = SurveyQuestionsModel(
                    data = listOf(
                        SurveyDataModel(id = UUID.randomUUID().toString())
                    )
                )
            )
        )
        val surveyModel2 = SurveyModel(
            id = UUID.randomUUID().toString(),
            type = "type_testing_2",
            attributes = SurveyAttributesModel(title = "title_testing_2"),
            relationships = SurveyRelationshipModel(
                questions = SurveyQuestionsModel(
                    data = listOf(
                        SurveyDataModel(id = UUID.randomUUID().toString())
                    )
                )
            )
        )
        val metaModel = MetaModel(page = 1, pageSize = 2, pages = 1, records = 2)
        val pair = Pair(metaModel, listOf(surveyModel1, surveyModel2))
        `when`(surveyRepository.getSurveyList(anyInt(), anyInt()))
            .thenReturn(Observable.just(pair))
        `when`(surveyRepository.getUserProfile())
            .thenReturn(Observable.just(UserModel()))

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        onView(withText("title_testing_1"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.viewPager))
            .perform(ViewActions.swipeLeft())

        onView(withText("title_testing_2"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validateNavigateToDetail_whenClickingButtonStartSurvey() {
        `when`(surveyRepository.getSurveyList(anyInt(), anyInt()))
            .thenReturn(Observable.just(Pair(MetaModel(), listOf())))
        `when`(surveyRepository.getUserProfile())
            .thenReturn(Observable.just(UserModel()))
        doNothing().`when`(mockNavController).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        onView(withId(R.id.btnStartSurvey))
            .perform(ViewActions.click())

        verify(mockNavController, times(1)).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
    }
}
