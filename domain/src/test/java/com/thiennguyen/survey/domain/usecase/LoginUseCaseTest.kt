package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.Password
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.domain.usecase.base.BaseUseCaseTest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginUseCaseTest : BaseUseCaseTest() {

    private val repository: AuthenticationRepository = mock()

    private lateinit var useCase: LoginUseCase

    override fun setUp() {
        super.setUp()

        useCase = LoginUseCase(repository)
    }

    @Test
    fun `When repository return complete login, then use case return completion`() {
        val email = Email("")
        val password = Password("")
        val testObserver = TestObserver<Unit>()

        whenever(repository.submitLogin(email, password)).thenReturn(Completable.complete())
        useCase.submitLogin(email, password).subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

    @Test
    fun `When repository return error while logging, then use case emit error`() {
        val email = Email("")
        val password = Password("")
        val testObserver = TestObserver<Unit>()

        whenever(repository.submitLogin(email, password)).thenReturn(Completable.error(NullPointerException()))
        useCase.submitLogin(email, password).subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }
    }
}
