package com.thiennguyen.survey.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thiennguyen.survey.R
import com.thiennguyen.survey.domain.model.SurveyModel

class PagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {

    private var currentSurveyList: List<SurveyModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(surveyList: List<SurveyModel>) {
        currentSurveyList = surveyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false))
    }

    override fun getItemCount(): Int = currentSurveyList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(currentSurveyList[position])
    }
}

