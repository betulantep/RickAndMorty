package com.betulantep.rickandmorty.data.repo.pagingsource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulantep.rickandmorty.data.entities.episode.Episode
import com.betulantep.rickandmorty.data.retrofit.AppRemoteDao
import com.betulantep.rickandmorty.utils.Constants.FIRST_PAGE_INDEX
import javax.inject.Inject

class EpisodePagingSource @Inject constructor(var remoteDao: AppRemoteDao) : PagingSource<Int,Episode>() {
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val nextPage : Int = params.key ?: FIRST_PAGE_INDEX
            val response = remoteDao.getAllEpisodes(nextPage)
            var nextPageNumber : Int? = null

            val totalPageCount = response.info.pages
            nextPageNumber = if(nextPage == totalPageCount){
                // bu if olmazsa parse edemediği için ilerlemiyor ve son sayfa gözükmüyor
                null //son sayfanın next değeri null
            }else{
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.episodes,
                prevKey = null,
                nextKey = nextPageNumber
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }

    }
}