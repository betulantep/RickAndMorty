package com.betulantep.rickandmorty.presentation.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.betulantep.rickandmorty.domain.uimodel.EpisodeUIModel
import com.betulantep.rickandmorty.domain.usecase.GetEpisodeUIModelUseCase
import com.betulantep.rickandmorty.domain.usecase.GetFilterEpisodeUIModelUseCase
import com.betulantep.rickandmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    var getEpisodeUIModelUseCase: GetEpisodeUIModelUseCase,
    var getFilterEpisodeUIModelUseCase: GetFilterEpisodeUIModelUseCase
) : ViewModel() {
    private val _episodeList = MutableStateFlow<PagingData<EpisodeUIModel>>(PagingData.empty())
    val episodeList = _episodeList.asStateFlow()

    private val _episodeLoading = MutableStateFlow(false)
    val episodeLoading = _episodeLoading.asStateFlow()

    private val _episodeError = MutableStateFlow("")
    val episodeError = _episodeError.asStateFlow()

    val seasonHashMap: MutableMap<String, String> = mutableMapOf()
    val episodeHashMap: MutableMap<String, String> = mutableMapOf()
    val episodeListHashMap = ArrayList<String>(emptyList())
    val seasonListHashMap = ArrayList<String>(emptyList())

    private var job: Job? = null

    init {
        getEpisodes()
    }

    fun getEpisodes() {
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

    fun getFilterEpisodes(filter: String) {
        job?.cancel()
        job = getFilterEpisodeUIModelUseCase.executeGetFilterEpisodes(viewModelScope, filter)
            .onEach { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        _episodeLoading.value = true
                    }
                    is NetworkResult.Success -> {
                        networkResult.data?.let {
                            _episodeList.value = it
                            _episodeLoading.value = false
                        }
                    }
                    is NetworkResult.Error -> {
                        _episodeError.value = networkResult.message ?: "Error! No Data"
                        _episodeLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun setHashMapSeasonAndEpisode(){
        seasonHashMap.clear()
        episodeHashMap.clear()
        seasonListHashMap.clear()
        episodeListHashMap.clear()
        //Seasons
        seasonHashMap["S01"] = "Season 1"
        seasonHashMap["S02"] = "Season 2"
        seasonHashMap["S03"] = "Season 3"
        seasonHashMap["S04"] = "Season 4"
        seasonHashMap["S05"] = "Season 5"


        for (seasonName in seasonHashMap){
            seasonListHashMap.add(seasonName.value)
        }

        //Episodes
        episodeHashMap["E01"] = "Episode 1"
        episodeHashMap["E02"] = "Episode 2"
        episodeHashMap["E03"] = "Episode 3"
        episodeHashMap["E04"] = "Episode 4"
        episodeHashMap["E05"] = "Episode 5"
        episodeHashMap["E06"] = "Episode 6"
        episodeHashMap["E07"] = "Episode 7"
        episodeHashMap["E08"] = "Episode 8"
        episodeHashMap["E09"] = "Episode 9"
        episodeHashMap["E10"] = "Episode 10"
        episodeHashMap["E11"] = "Episode 11"

        for (episodeName in episodeHashMap){
            episodeListHashMap.add(episodeName.value)
        }

    }


}