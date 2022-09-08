package com.betulantep.rickandmorty.presentation.episode

import com.betulantep.rickandmorty.domain.uimodel.EpisodeUIModel

interface EpisodeItemClickListener {
    fun onItemClickListener(episodeUIModel: EpisodeUIModel)
}