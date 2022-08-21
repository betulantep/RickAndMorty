package com.betulantep.rickandmorty.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.data.entities.Character.CharacterResponse
import com.betulantep.rickandmorty.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

interface AppRepository {

    suspend fun getCharacters(): Flow<PagingData<Character>>
}