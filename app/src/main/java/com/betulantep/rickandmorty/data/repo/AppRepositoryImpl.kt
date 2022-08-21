package com.betulantep.rickandmorty.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.retrofit.AppRemoteDao
import com.betulantep.rickandmorty.ui.character.CharacterPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(var remoteDao: AppRemoteDao) : AppRepository {

    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(42),
            pagingSourceFactory = { CharacterPagingSource(remoteDao) }).flow
    }
}