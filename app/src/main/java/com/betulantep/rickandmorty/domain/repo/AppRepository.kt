package com.betulantep.rickandmorty.domain.repo

import androidx.paging.PagingData
import com.betulantep.rickandmorty.data.entities.character.Character
import com.betulantep.rickandmorty.data.entities.character.CharacterResponse
import com.betulantep.rickandmorty.data.entities.episode.Episode
import com.betulantep.rickandmorty.data.entities.episode.EpisodeResponse
import com.betulantep.rickandmorty.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepository {

    suspend fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getAllEpisodes(): Flow<PagingData<Episode>>

    suspend fun getFilterCharacters(filterQuery: Map<String,String>): Flow<PagingData<Character>>

    suspend fun getFilterEpisodes(filter: String): Flow<PagingData<Episode>>

    suspend fun getCharactersNetworkResult(pageNumber : Int) : CharacterResponse
    suspend fun getEpisodesNetworkResult(pageNumber : Int) : EpisodeResponse

    suspend fun getCharacterByIdList(ids:String): List<Character>
}