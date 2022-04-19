package com.thiennguyen.survey.feature.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.domain.usecase.LoginUseCase
import com.thiennguyen.survey.utils.RxUtils
import com.thiennguyen.survey.utils.add
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
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

    val onLoginStateChanged = MutableLiveData<LoginState>()

    fun submitLogin(email: String, password: String) {
        when {
            email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                onLoginStateChanged.postValue(LoginState.EmailInvalid)
            }
            password.isBlank() -> {
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