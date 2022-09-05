package com.betulantep.rickandmorty.data.retrofit

import com.betulantep.rickandmorty.data.entities.character.CharacterResponse
import com.betulantep.rickandmorty.data.entities.episode.EpisodeResponse
import retrofit2.http.GET
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

}