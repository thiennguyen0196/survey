package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.UserAttributesModel
import com.thiennguyen.survey.domain.model.UserModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import com.thiennguyen.survey.domain.usecase.base.BaseUseCaseTest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import java.util.UUID
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserProfileUseCaseTest : BaseUseCaseTest() {

    private val repository: SurveyRepository = mock()

    private lateinit var useCase: GetUserProfileUseCase

    override fun setUp() {
        super.setUp()

        useCase = GetUserProfileUseCase(repository)
    }

    @Test
    fun `When repository return user profile, then return its correct value`() {
        val userModel = UserModel(
            id = UUID.randomUUID().toString(),
            type = "type_testing",
            attributes = UserAttributesModel(email = "abc@gmai.com", avatarUrl = "https://google.com")
        )

        val testObserver = TestObserver<UserModel>()

        whenever(repository.getUserProfile()).thenReturn(Observable.just(userModel))
        useCase.getUserProfile().subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.id == userModel.id &&
                it.type == userModel.type &&
                it.attributes == userModel.attributes
        }
    }

    @Test
    fun `When repository return null, then use case emit error`() {
        val testObserver = TestObserver<UserModel>()

        whenever(repository.getUserProfile()).thenReturn(Observable.error(NullPointerException()))
        useCase.getUserProfile().subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.assertError { it is NullPointerException }
    }
}
