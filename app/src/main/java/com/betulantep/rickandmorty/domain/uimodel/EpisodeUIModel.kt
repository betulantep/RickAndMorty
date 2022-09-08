package com.betulantep.rickandmorty.domain.uimodel

import java.io.Serializable

data class EpisodeUIModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val url: String
):Serializable
