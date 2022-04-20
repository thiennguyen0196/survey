package com.thiennguyen.survey.feature.home

import com.thiennguyen.survey.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        onLoadingChange.postValue(true)
    }
}