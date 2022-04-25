package com.thiennguyen.survey.feature.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thiennguyen.survey.databinding.ItemPagerBinding
import com.thiennguyen.survey.domain.model.SurveyModel

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPagerBinding.bind(itemView)

    fun bind(model: SurveyModel) {
        binding.tvTitle.text = model.attributes?.title
        binding.tvDesc.text = model.attributes?.description
        Glide.with(itemView.context)
            .load("${model.attributes?.coverImageUrl}l")
            .centerCrop()
            .into(binding.ivCover)
    }
}
