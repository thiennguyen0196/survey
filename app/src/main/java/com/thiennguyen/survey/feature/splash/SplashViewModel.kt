package com.thiennguyen.survey.feature.splash

import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.domain.usecase.CheckIsLoggedInUseCase
import com.thiennguyen.survey.utils.RxUtils
import com.thiennguyen.survey.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkIsLoggedInUseCase: CheckIsLoggedInUseCase
) : BaseViewModel() {

    sealed class LoggedInState {
        object LoggedIn : LoggedInState()
        object NotLoggedIn : LoggedInState()
    }

    val onIsLoggedInStateChange = SingleLiveData<LoggedInState>()

    fun checkIsLoggedIn() {
        checkIsLoggedInUseCase.checkIsLoggedIn()
            .compose(RxUtils.applyObservableSchedulers())
            .subscribe({
                when (it) {
                    true -> onIsLoggedInStateChange.postValue(LoggedInState.LoggedIn)
                    false -> onIsLoggedInStateChange.postValue(LoggedInState.NotLoggedIn)
                }
            }, {
                Timber.i(it)
                onIsLoggedInStateChange.postValue(LoggedInState.NotLoggedIn)
            })
    }
}
