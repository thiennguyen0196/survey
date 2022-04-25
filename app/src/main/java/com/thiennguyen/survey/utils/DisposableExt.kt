package com.thiennguyen.survey.utils

import com.thiennguyen.survey.base.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.add(baseViewModel: BaseViewModel) {
    baseViewModel.addDisposable(this)
}
