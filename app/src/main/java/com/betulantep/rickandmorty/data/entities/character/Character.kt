package com.betulantep.rickandmorty.data.entities.character


import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

fun Character.toCharacterUIModel(): CharacterUIModel {
    return CharacterUIModel(
        id, name, status, gender, species, created, episode, image, location, origin, url
    )
}