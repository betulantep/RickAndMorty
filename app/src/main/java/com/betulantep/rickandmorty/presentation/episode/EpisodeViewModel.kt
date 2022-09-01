package com.betulantep.rickandmorty.presentation.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.betulantep.rickandmorty.domain.repo.AppRepository
import com.betulantep.rickandmorty.domain.uimodel.EpisodeUIModel
import com.betulantep.rickandmorty.domain.usecase.GetEpisodeUIModelUseCase
import com.betulantep.rickandmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(var getEpisodeUIModelUseCase: GetEpisodeUIModelUseCase) :ViewModel(){
    private val _episodeList = MutableStateFlow<PagingData<EpisodeUIModel>>(PagingData.empty())
    val episodeList = _episodeList.asStateFlow()

    private val _episodeLoading = MutableStateFlow(false)
    val episodeLoading = _episodeLoading.asStateFlow()

    private val _episodeError = MutableStateFlow("")
    val episodeError = _episodeError.asStateFlow()

    private var job : Job? = null

    init {
        getCharacters()
    }

    fun getCharacters() {
        job?.cancel()
        job = getEpisodeUIModelUseCase.executeGetEpisode(viewModelScope)
            .onEach { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        _episodeLoading.value = true
                        Log.e("asd", "episode loading")
                    }
                    is NetworkResult.Success -> {
                        networkResult.data?.let {
                            _episodeList.value = it
                            _episodeLoading.value = false
                            Log.e("asd", "episode data")
                        }
                    }
                    is NetworkResult.Error -> {
                        _episodeError.value = networkResult.message ?: "Error! No Data"
                        _episodeLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
    }
}