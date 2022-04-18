package com.thiennguyen.survey.base

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null || compositeDisposable?.isDisposed == true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun dispose() {
        compositeDisposable?.dispose()
    }

    override fun onCleared() {
        compositeDisposable?.dispose()
        super.onCleared()
    }
}