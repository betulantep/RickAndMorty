package com.betulantep.rickandmorty.data.repo.pagingsource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.data.retrofit.AppRemoteDao
import com.betulantep.rickandmorty.utils.Constants
import javax.inject.Inject

class FilterCharacterPagingSource @Inject constructor(var remoteDao: AppRemoteDao, var filter: Map<String,String>) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage: Int = params.key ?: Constants.FIRST_PAGE_INDEX
            val response = remoteDao.getFilterCharacters(query = nextPage, filterQuery = filter)
            var nextPageNumber: Int? = null

            val uri = Uri.parse(response.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery!!.toInt()

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