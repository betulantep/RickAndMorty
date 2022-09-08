package com.betulantep.rickandmorty.data.retrofit

import com.betulantep.rickandmorty.data.entities.character.Character
import com.betulantep.rickandmorty.data.entities.character.CharacterResponse
import com.betulantep.rickandmorty.data.entities.episode.EpisodeResponse
import com.betulantep.rickandmorty.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AppRemoteDao {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") query: Int): CharacterResponse

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") query: Int): EpisodeResponse

    @GET("character")
    suspend fun getFilterCharacters(@Query("page") query: Int, @QueryMap filterQuery: Map<String, String>): CharacterResponse

    @GET("episode")
    suspend fun getFilterEpisodes(@Query("page") query: Int, @Query("episode") filter: String): EpisodeResponse

    @GET("character/{ids}")
    suspend fun getCharacterByIdList(@Path("ids") ids: String): List<Character>

}