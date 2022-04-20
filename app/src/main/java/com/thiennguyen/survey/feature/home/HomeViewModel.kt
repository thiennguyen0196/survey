package com.thiennguyen.survey.feature.home

import com.thiennguyen.survey.base.BaseViewModel
import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.usecase.GetSurveyListUseCase
import com.thiennguyen.survey.utils.RxUtils
import com.thiennguyen.survey.utils.SingleLiveData
import com.thiennguyen.survey.utils.add
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase
) : BaseViewModel() {

    data class LoadMoreDataSet(
        var page: Int = Constants.Page.PAGE_NUMBER,
        val pageSize: Int = Constants.Page.PAGE_SIZE,
        var isLoading: Boolean = false,
        var isHasMore: Boolean = true
    )

    var loadMoreDataSet = LoadMoreDataSet()
    val onSurveyListChange = SingleLiveData<List<SurveyModel>>()

    init {
        getSurveyList()
    }

    fun getSurveyList(isRefresh: Boolean = false) {
        if (isRefresh) {
            dispose()
            loadMoreDataSet = LoadMoreDataSet()
        }
        if (!loadMoreDataSet.isHasMore || loadMoreDataSet.isLoading) return
        loadMoreDataSet.isLoading = true
        getSurveyListUseCase.getSurveyList(loadMoreDataSet.page, loadMoreDataSet.pageSize)
            .compose(RxUtils.applyObservableSchedulers(onLoadingChange))
            .subscribe({
                calculateLoadMoreDataSet(it.first)
                it.second.let { list ->
                    onSurveyListChange.postValue(list)
                }
            }, {
                Timber.i(it)
                onErrorChange.postValue(it)
                loadMoreDataSet.isLoading = false
            }).add(this)
    }

    private fun calculateLoadMoreDataSet(meta: MetaModel) {
        loadMoreDataSet.isLoading = false
        loadMoreDataSet.page = (meta.page ?: Constants.Page.PAGE_NUMBER) + 1
        if (loadMoreDataSet.page > meta.pages ?: Constants.Page.PAGE_NUMBER) {
            loadMoreDataSet.isHasMore = false
        }
    }
}