package com.betulantep.rickandmorty.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.data.entities.Character.CharacterResponse
import com.betulantep.rickandmorty.data.repo.pagingsource.CharacterPagingSource
import com.betulantep.rickandmorty.data.repo.pagingsource.FilterCharacterPagingSource
import com.betulantep.rickandmorty.data.retrofit.AppRemoteDao
import com.betulantep.rickandmorty.domain.repo.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(var remoteDao: AppRemoteDao) : AppRepository {

    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(42),
            pagingSourceFactory = { CharacterPagingSource(remoteDao)}).flow
    }

    override suspend fun getFilterCharacters(filterQuery: Map<String, String>): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { FilterCharacterPagingSource(remoteDao,filterQuery)}).flow
    }

    override suspend fun getCharactersNetworkResult(pageNumber: Int): CharacterResponse {
        return remoteDao.getAllCharacters(pageNumber)
    }
}