package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.domain.usecase.base.BaseUseCaseTest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckIsLoggedInUseCaseTest : BaseUseCaseTest() {

    private val repository: AuthenticationRepository = mock()

    private lateinit var useCase: CheckIsLoggedInUseCase

    override fun setUp() {
        super.setUp()

        useCase = CheckIsLoggedInUseCase(repository)
    }

    @Test
    fun `When user has already logged in, return true`() {
        whenever(repository.isLoggedIn()).thenReturn(Observable.just(true))

        val testObserver = TestObserver<Boolean>()

        useCase.checkIsLoggedIn().subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(true)
    }

    @Test
    fun `When user has not logged in, return false`() {
        whenever(repository.isLoggedIn()).thenReturn(Observable.just(false))

        val testObserver = TestObserver<Boolean>()

        useCase.checkIsLoggedIn().subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(false)
    }
}
