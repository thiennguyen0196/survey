package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.base.BaseUseCaseTest
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ResetPasswordUseCaseTest : BaseUseCaseTest() {

    private val repository: AuthenticationRepository = mock()

    private lateinit var useCase: ResetPasswordUseCase

    override fun setUp() {
        super.setUp()

        useCase = ResetPasswordUseCase(repository)
    }

    @Test
    fun `When repository return complete reset password, then use case return meta model with custom message`() {
        val email = Email("")
        val metaModel = MetaModel(message = "Success reset password")
        val testObserver = TestObserver<MetaModel>()

        whenever(repository.resetPassword(email)).thenReturn(Observable.just(metaModel))
        useCase.resetPassword(email).subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it.message == metaModel.message }
    }

    @Test
    fun `When repository return complete reset password, then use case emits error`() {
        val email = Email("")
        val testObserver = TestObserver<MetaModel>()

        whenever(repository.resetPassword(email)).thenReturn(Observable.error(NullPointerException()))
        useCase.resetPassword(email).subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.assertError { it is NullPointerException }
    }
}
