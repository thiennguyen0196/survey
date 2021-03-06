package com.thiennguyen.survey.feature.forgot

import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.usecase.ResetPasswordUseCase
import com.thiennguyen.survey.utils.RxUtils
import com.thiennguyen.survey.utils.SingleLiveData
import com.thiennguyen.survey.utils.add
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel@Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel() {

    sealed class ResetPasswordState {
        data class Success(val message: String) : ResetPasswordState()
        object EmailInvalid : ResetPasswordState()
    }

    val onResetPasswordChange = SingleLiveData<ResetPasswordState>()

    fun resetPassword(email: Email) {
        when {
            email.isValid().not() -> {
                onResetPasswordChange.postValue(ResetPasswordState.EmailInvalid)
            }
            else -> {
                resetPasswordUseCase.resetPassword(email)
                    .compose(RxUtils.applyObservableSchedulers(onLoadingChange))
                    .subscribe({
                        onResetPasswordChange.postValue(ResetPasswordState.Success(it.message.orEmpty()))
                    }, {
                        onErrorChange.postValue(it)
                    }).add(this)
            }
        }
    }
}
