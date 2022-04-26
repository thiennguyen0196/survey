package com.thiennguyen.survey.feature.forgot

import com.thiennguyen.survey.base.BaseViewModelTest
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.usecase.ResetPasswordUseCase
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

class ForgotViewModelTest : BaseViewModelTest() {

    private val useCase: ResetPasswordUseCase = mock()

    private lateinit var viewModel: ForgotViewModel

    override fun setUp() {
        super.setUp()

        viewModel = spy(ForgotViewModel(useCase))
    }

    @Test
    fun `When email is invalid, then reset password state emits invalid email value`() {
        val email = Email("abc@gmail")
        viewModel.resetPassword(email)

        viewModel.onResetPasswordChange.observeForever {
            Assert.assertTrue(viewModel.onResetPasswordChange.value is ForgotViewModel.ResetPasswordState.EmailInvalid)
        }
    }

    @Test
    fun `When email is valid and reset password function is called and return valid response, then reset password state emits correct message`() {
        val email = Email("abc@gmail.com")
        val metaModel = MetaModel(message = "Success")

        whenever(useCase.resetPassword(email)).thenReturn(Observable.just(metaModel))

        viewModel.resetPassword(email)

        viewModel.onResetPasswordChange.observeForever {
            Assert.assertEquals(ForgotViewModel.ResetPasswordState.Success(metaModel.message.orEmpty()), it)
        }
    }

    @Test
    fun `When email is valid and reset password function is called and return error, then error change emits error`() {
        val email = Email("abc@gmail.com")

        whenever(useCase.resetPassword(email)).thenReturn(Observable.error(NullPointerException()))

        viewModel.resetPassword(email)

        viewModel.onResetPasswordChange.observeForever {
            Assert.assertNull(it)
        }
        viewModel.onErrorChange.observeForever {
            Assert.assertTrue(it is NullPointerException)
        }
    }
}
