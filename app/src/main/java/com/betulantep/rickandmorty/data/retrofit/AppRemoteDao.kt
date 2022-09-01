package com.betulantep.rickandmorty.data.retrofit

import com.betulantep.rickandmorty.data.entities.character.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AppRemoteDao {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") query: Int): CharacterResponse

    @GET("character")
    suspend fun getFilterCharacters(
        @Query("page") query: Int,
        @QueryMap filterQuery: Map<String, String>
    ): CharacterResponse

}