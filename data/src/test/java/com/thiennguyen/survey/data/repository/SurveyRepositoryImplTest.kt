package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.base.BaseRepositoryTest
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.model.UserModel
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class SurveyRepositoryImplTest : BaseRepositoryTest() {

    companion object {
        private const val SERVER_FAIL = "api-response/server_fail.json"
        private const val SURVEY_LIST_RESPONSE = "api-response/survey_list_response.json"
        private const val SURVEY_LIST_META_NULL_RESPONSE = "api-response/survey_list_meta_null_response.json"
        private const val SURVEY_LIST_DATA_NULL_RESPONSE = "api-response/survey_list_data_null_response.json"
        private const val USER_PROFILE_RESPONSE = "api-response/user_profile_response.json"
        private const val DATA_NULL_RESPONSE = "api-response/data_null_response.json"
    }

    private lateinit var repository: SurveyRepositoryImpl

    override fun setUp() {
        super.setUp()

        val service = retrofit.create(SurveyService::class.java)
        repository = Mockito.spy(SurveyRepositoryImpl(surveyService = service))
    }

    @Test
    fun `When getting survey list returns success, then return complete`() {
        fakeResponseBodyFile(SURVEY_LIST_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()
        repository.getSurveyList(1, 2).subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.first.page == 1 && it.first.pageSize == 2 && it.second.size == 2
        }
    }

    @Test
    fun `When getting survey list returns meta null, then return error`() {
        fakeResponseBodyFile(SURVEY_LIST_META_NULL_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()
        repository.getSurveyList(1, 2).subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }
    }

    @Test
    fun `When getting survey list returns data null, then return error`() {
        fakeResponseBodyFile(SURVEY_LIST_DATA_NULL_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()
        repository.getSurveyList(1, 2).subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }
    }


    @Test
    fun `When getting survey list fail, then return error`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = TestObserver<Pair<MetaModel, List<SurveyModel>>>()
        repository.getSurveyList(1, 2).subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is HttpException }
    }

    @Test
    fun `When getting user profile returns success, then return complete`() {
        fakeResponseBodyFile(USER_PROFILE_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<UserModel>()
        repository.getUserProfile().subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it.id == "1" }
    }

    @Test
    fun `When getting user profile returns success, but data is null, then return error`() {
        fakeResponseBodyFile(DATA_NULL_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<UserModel>()
        repository.getUserProfile().subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }
    }

    @Test
    fun `When getting user profile fail, then return error`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = TestObserver<UserModel>()
        repository.getUserProfile().subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is HttpException }
    }
}