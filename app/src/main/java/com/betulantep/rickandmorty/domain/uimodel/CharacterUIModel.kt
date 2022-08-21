package com.betulantep.rickandmorty.domain.uimodel

import com.betulantep.rickandmorty.data.entities.Character.Location
import com.betulantep.rickandmorty.data.entities.Character.Origin

data class CharacterUIModel(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val created: String,
    val episode: List<String>,
    val image: String,
    val location: Location,
    val origin: Origin,
    val url: String
)
