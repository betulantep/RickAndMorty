package com.betulantep.rickandmorty.data.entities.episode


import com.betulantep.rickandmorty.domain.uimodel.EpisodeUIModel
import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

fun Episode.toEpisodeUIModel(): EpisodeUIModel {
    return EpisodeUIModel(
        id, name, airDate, characters, created, episode, url
    )
}