package com.thiennguyen.survey.feature.home

import com.thiennguyen.survey.base.BaseViewModelTest
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyAttributesModel
import com.thiennguyen.survey.domain.model.SurveyDataModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.model.SurveyQuestionsModel
import com.thiennguyen.survey.domain.model.SurveyRelationshipModel
import com.thiennguyen.survey.domain.model.UserAttributesModel
import com.thiennguyen.survey.domain.model.UserModel
import com.thiennguyen.survey.domain.usecase.GetSurveyListUseCase
import com.thiennguyen.survey.domain.usecase.GetUserProfileUseCase
import io.reactivex.rxjava3.core.Observable
import java.util.UUID
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HomeViewModelTest : BaseViewModelTest() {

    private val getSurveyListUseCase: GetSurveyListUseCase = mock()
    private val getUserProfileUseCase: GetUserProfileUseCase = mock()

    private lateinit var viewModel: HomeViewModel

    private val surveyModel1 = SurveyModel(
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
    private val surveyModel2 = SurveyModel(
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
    private val metaModel = MetaModel(page = 1, pageSize = 2, pages = 10, records = 20)
    private val pair = Pair(metaModel, listOf(surveyModel1, surveyModel2))

    override fun setUp() {
        whenever(getSurveyListUseCase.getSurveyList(any(), any())).thenReturn(Observable.just(pair))
        whenever(getUserProfileUseCase.getUserProfile()).thenReturn(Observable.just(UserModel()))
        viewModel = spy(HomeViewModel(getSurveyListUseCase, getUserProfileUseCase))

        super.setUp()
    }

    @Test
    fun `When view model is init, then get survey list use case and get user profile use case are called`() {
        verify(getSurveyListUseCase, times(1)).getSurveyList(any(), any())
        verify(getUserProfileUseCase, times(1)).getUserProfile()
    }

    @Test
    fun `When get survey list use case return success response, then survey list change emits correct state with value`() {
        whenever(getSurveyListUseCase.getSurveyList(any(), any())).thenReturn(Observable.just(pair))

        viewModel.getSurveyList()

        viewModel.onSurveyListChange.observeForever {
            Assert.assertEquals(2, it.size)
            Assert.assertEquals(surveyModel1.id, it[0].id)
            Assert.assertEquals(surveyModel2.id, it[1].id)
            Assert.assertEquals(false, viewModel.loadMoreDataSet.isLoading)
            Assert.assertEquals(true, viewModel.loadMoreDataSet.isHasMore)
            Assert.assertEquals(2, viewModel.loadMoreDataSet.page)
        }
    }

    @Test
    fun `When get survey list use case return error, then error change emits error`() {
        whenever(getSurveyListUseCase.getSurveyList(any(), any())).thenReturn(Observable.error(NullPointerException()))

        viewModel.getSurveyList()

        viewModel.onSurveyListChange.observeForever {
            Assert.assertNull(it)
        }
        viewModel.onErrorChange.observeForever {
            Assert.assertTrue(it is NullPointerException)
        }
    }

    @Test
    fun `When get user profile use case return success response, then user profile avatar url emits correct url`() {
        whenever(getUserProfileUseCase.getUserProfile()).thenReturn(
            Observable.just(UserModel(attributes = UserAttributesModel(avatarUrl = "https://google.com")))
        )

        viewModel.getUserProfile()

        viewModel.onUserProfileAvatarChange.observeForever {
            Assert.assertEquals("https://google.com", it)
        }
    }

    @Test
    fun `When get user profile use case return error, then user profile avatar url emits nothing`() {
        whenever(getUserProfileUseCase.getUserProfile()).thenReturn(Observable.error(NullPointerException()))

        viewModel.getUserProfile()

        viewModel.onUserProfileAvatarChange.observeForever {
            Assert.assertNull(it)
        }
    }

    @Test
    fun `When meta model has current page smaller than total pages, then has more data set is true`() {
        viewModel.calculateLoadMoreDataSet(MetaModel(pages = 5, page = 4))
        Assert.assertTrue(viewModel.loadMoreDataSet.isHasMore)
        Assert.assertEquals(5, viewModel.loadMoreDataSet.page)
    }

    @Test
    fun `When meta model has current page equal total pages, then has more data set is false`() {
        viewModel.calculateLoadMoreDataSet(MetaModel(pages = 5, page = 5))
        Assert.assertFalse(viewModel.loadMoreDataSet.isHasMore)
        Assert.assertEquals(6, viewModel.loadMoreDataSet.page)
    }
}
