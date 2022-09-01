package com.betulantep.rickandmorty.domain.repo

import androidx.paging.PagingData
import com.betulantep.rickandmorty.data.entities.character.Character
import com.betulantep.rickandmorty.data.entities.character.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getFilterCharacters(filterQuery: Map<String,String>): Flow<PagingData<Character>>

    suspend fun getCharactersNetworkResult(pageNumber : Int) : CharacterResponse
}