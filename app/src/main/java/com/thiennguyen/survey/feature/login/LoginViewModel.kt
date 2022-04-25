package com.thiennguyen.survey.feature.login

import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.Password
import com.thiennguyen.survey.domain.usecase.LoginUseCase
import com.thiennguyen.survey.utils.RxUtils
import com.thiennguyen.survey.utils.SingleLiveData
import com.thiennguyen.survey.utils.add
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    sealed class LoginState {
        object Success : LoginState()
        object EmailInvalid : LoginState()
        object PasswordInvalid : LoginState()
    }

    val onLoginStateChanged = SingleLiveData<LoginState>()

    fun submitLogin(email: Email, password: Password) {
        when {
            email.isValid().not() -> {
                onLoginStateChanged.postValue(LoginState.EmailInvalid)
            }
            password.isValid().not() -> {
                onLoginStateChanged.postValue(LoginState.PasswordInvalid)
            }
            else -> {
                loginUseCase.submitLogin(email = email, password = password)
                    .compose(RxUtils.applyCompletableSchedulers(onLoadingChange))
                    .subscribe({
                        onLoginStateChanged.postValue(LoginState.Success)
                    }, {
                        onErrorChange.postValue(it)
                    }).add(this)
            }
        }
    }
}
