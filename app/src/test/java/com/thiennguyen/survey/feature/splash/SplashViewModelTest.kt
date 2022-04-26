package com.thiennguyen.survey.feature.splash

import com.thiennguyen.survey.base.BaseViewModelTest
import com.thiennguyen.survey.domain.usecase.CheckIsLoggedInUseCase
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

class SplashViewModelTest : BaseViewModelTest() {

    private val useCase: CheckIsLoggedInUseCase = mock()

    private lateinit var viewModel: SplashViewModel

    override fun setUp() {
        super.setUp()

        viewModel = spy(SplashViewModel(useCase))
    }

    @Test
    fun `When checking logged in use case return true, then logged in state emits already logged in`() {
        whenever(useCase.checkIsLoggedIn()).thenReturn(Observable.just(true))
        viewModel.checkIsLoggedIn()

        viewModel.onIsLoggedInStateChange.observeForever {
            Assert.assertTrue(it is SplashViewModel.LoggedInState.LoggedIn)
        }
    }

    @Test
    fun `When checking logged in use case return false, then logged in state emits not logged in`() {
        whenever(useCase.checkIsLoggedIn()).thenReturn(Observable.just(false))
        viewModel.checkIsLoggedIn()

        viewModel.onIsLoggedInStateChange.observeForever {
            Assert.assertTrue(it is SplashViewModel.LoggedInState.NotLoggedIn)
        }
    }

    @Test
    fun `When checking logged in use case return error, then logged in state emits not logged in`() {
        whenever(useCase.checkIsLoggedIn()).thenReturn(Observable.just(false))
        viewModel.checkIsLoggedIn()

        viewModel.onIsLoggedInStateChange.observeForever {
            Assert.assertTrue(it is SplashViewModel.LoggedInState.NotLoggedIn)
        }
    }
}
