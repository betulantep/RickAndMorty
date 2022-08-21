package com.betulantep.rickandmorty.ui.character

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.retrofit.AppRemoteDao
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
            if(response.body()?.info?.next != null){
                val uri = Uri.parse(response.body()!!.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery!!.toInt()
            }
            LoadResult.Page(
                data = response.body()!!.characters,
                prevKey = null,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}