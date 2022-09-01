package com.betulantep.rickandmorty.data.repo.pagingsource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulantep.rickandmorty.data.entities.character.Character
import com.betulantep.rickandmorty.data.retrofit.AppRemoteDao
import com.betulantep.rickandmorty.utils.Constants.FIRST_PAGE_INDEX
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(var remoteDao: AppRemoteDao) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = remoteDao.getAllCharacters(nextPage)
            var nextPageNumber: Int? = null

            val totalPageCount = response.info.pages
            nextPageNumber = if(nextPage == totalPageCount){
                // bu if olmazsa parse edemediği için ilerlemiyor ve son sayfa gözükmüyor
                null
            }else{
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.characters,
                prevKey = null,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}