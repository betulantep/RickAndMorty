package com.betulantep.rickandmorty.data.retrofit

import com.betulantep.rickandmorty.data.entities.Character.CharacterResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppRemoteDao {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") query:Int) : CharacterResponse

}