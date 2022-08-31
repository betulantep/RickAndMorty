package com.betulantep.rickandmorty.domain.repo

import androidx.paging.PagingData
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.data.entities.Character.CharacterResponse
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getFilterCharacters(filterQuery: Map<String,String>): Flow<PagingData<Character>>

    suspend fun getCharactersNetworkResult(pageNumber : Int) : CharacterResponse
}