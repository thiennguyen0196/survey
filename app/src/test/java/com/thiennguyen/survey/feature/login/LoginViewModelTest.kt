package com.thiennguyen.survey.feature.login

import com.thiennguyen.survey.base.BaseViewModelTest
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.Password
import com.thiennguyen.survey.domain.usecase.LoginUseCase
import io.reactivex.rxjava3.core.Completable
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

class LoginViewModelTest : BaseViewModelTest() {

    private val useCase: LoginUseCase = mock()

    private lateinit var viewModel: LoginViewModel

    override fun setUp() {
        super.setUp()

        viewModel = spy(LoginViewModel(useCase))
    }

    @Test
    fun `When email is invalid, then login state emits invalid email value`() {
        val email = Email("abc@gmail")
        val password = Password("")
        viewModel.submitLogin(email, password)

        viewModel.onLoginStateChanged.observeForever {
            Assert.assertTrue(it is LoginViewModel.LoginState.EmailInvalid)
        }
    }

    @Test
    fun `When email is valid but password is blank, then login state emits invalid password value`() {
        val email = Email("abc@gmail.com")
        val password = Password("")
        viewModel.submitLogin(email, password)

        viewModel.onLoginStateChanged.observeForever {
            Assert.assertTrue(it is LoginViewModel.LoginState.PasswordInvalid)
        }
    }

    @Test
    fun `When email and password are valid and submit login function is called and return complete success, then login state emits success`() {
        val email = Email("abc@gmail.com")
        val password = Password("abc123")

        whenever(useCase.submitLogin(email, password)).thenReturn(Completable.complete())

        viewModel.submitLogin(email, password)

        viewModel.onLoginStateChanged.observeForever {
            Assert.assertTrue(it is LoginViewModel.LoginState.Success)
        }
    }

    @Test
    fun `When email and password are valid and submit login function is called but return error, then error change emits error`() {
        val email = Email("abc@gmail.com")
        val password = Password("abc123")

        whenever(useCase.submitLogin(email, password)).thenReturn(Completable.error(NullPointerException()))

        viewModel.submitLogin(email, password)

        viewModel.onLoginStateChanged.observeForever {
            Assert.assertNull(it)
        }
        viewModel.onErrorChange.observeForever {
            Assert.assertTrue(it is NullPointerException)
        }
    }
}
