package com.thiennguyen.survey.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

object RxUtils {

    fun <T : Any> applyObservableSchedulers(loading: MutableLiveData<Boolean>? = null): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading?.postValue(true) }
                .doOnNext { loading?.postValue(false) }
                .doAfterTerminate { loading?.postValue(false) }
        }
    }

    fun applyCompletableSchedulers(loading: MutableLiveData<Boolean>? = null): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading?.postValue(true) }
                .doOnComplete { loading?.postValue(false) }
                .doAfterTerminate { loading?.postValue(false) }
        }
    }
}
