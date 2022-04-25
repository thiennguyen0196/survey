package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyAttributesModel
import com.thiennguyen.survey.domain.model.SurveyDataModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.model.SurveyQuestionsModel
import com.thiennguyen.survey.domain.model.SurveyRelationshipModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import com.thiennguyen.survey.domain.usecase.base.BaseUseCaseTest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import java.util.UUID
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetSurveyListUseCaseTest : BaseUseCaseTest() {

    private val repository: SurveyRepository = mock()

    private lateinit var useCase: GetSurveyListUseCase

    override fun setUp() {
        super.setUp()

        useCase = GetSurveyListUseCase(repository)
    }

    @Test
    fun `When repository return list of survey, then return its correct value`() {
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
        val metaModel = MetaModel(page = 1, pageSize = 2, pages = 10, records = 20)
        val pair = Pair(metaModel, listOf(surveyModel1, surveyModel2))

        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()

        whenever(repository.getSurveyList(any(), any())).thenReturn(Observable.just(pair))
        useCase.getSurveyList(0, 0).subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.first.pages == 10 &&
                it.second.size == 2 &&
                it.second[0].id == surveyModel1.id &&
                it.second[1].id == surveyModel2.id
        }
    }

    @Test
    fun `When repository return null, then use case emit error`() {
        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()

        whenever(repository.getSurveyList(any(), any())).thenReturn(Observable.error(NullPointerException()))
        useCase.getSurveyList(0, 0).subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.assertError { it is NullPointerException }
    }
}
