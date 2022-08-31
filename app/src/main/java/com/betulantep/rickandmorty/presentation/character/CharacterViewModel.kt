package com.betulantep.rickandmorty.presentation.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.betulantep.rickandmorty.domain.repo.AppRepository
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import com.betulantep.rickandmorty.domain.usecase.GetCharacterUIModelUseCase
import com.betulantep.rickandmorty.domain.usecase.GetFilterCharacterUIModelUseCase
import com.betulantep.rickandmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    var getCharacterUIModelUseCase: GetCharacterUIModelUseCase,
    var getFilterCharacterUIModelUseCase: GetFilterCharacterUIModelUseCase
) : ViewModel() {

    private val _characterList = MutableStateFlow<PagingData<CharacterUIModel>>(PagingData.empty())
    val characterList: StateFlow<PagingData<CharacterUIModel>> = _characterList

    private val _characterLoading = MutableStateFlow(false)
    val characterLoading: StateFlow<Boolean> = _characterLoading

    private val _characterError = MutableStateFlow("")
    val characterError: StateFlow<String> = _characterError

    private val _characterStatus = MutableStateFlow("Alive")
    val characterStatus = _characterStatus.asStateFlow()

    private val _characterSpecies = MutableStateFlow("Human")
    val characterSpecies = _characterSpecies.asStateFlow()

    private val _characterGender = MutableStateFlow("Male")
    val characterGender = _characterGender.asStateFlow()

    private val _filterQueryMap = MutableStateFlow<Map<String,String>?>(null)
    private val filterQueryMap = _filterQueryMap.asStateFlow()

    val backFromBottomSheet : MutableLiveData<Boolean> = MutableLiveData()
    private var job: Job? = null

    init {
        getCharacters()
        Log.e("asd", "init")
    }

    fun changeValueCharacterStatus(status: String) { _characterStatus.value = status }

    fun changeValueCharacterSpecies(species: String) { _characterSpecies.value = species }

    fun changeValueCharacterGender(gender: String) { _characterGender.value = gender }

    private fun setFilterQueryMap(){
        val mapData : MutableMap<String,String> = HashMap()
        mapData["status"] = characterStatus.value
        mapData["species"] = characterSpecies.value
        mapData["gender"] = characterGender.value

        _filterQueryMap.value = mapData
    }

    fun searchCharacterName(searchName: String) {
        _characterList.value = _characterList.value.filter {
            it.name.lowercase().contains(searchName.lowercase())
        }
    }
    fun getCharacters() {
        job?.cancel()
            job = getCharacterUIModelUseCase.executeGetCharacters(viewModelScope)
                .onEach { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Loading -> {
                            _characterLoading.value = true
                            Log.e("asd", "loading")
                        }
                        is NetworkResult.Success -> {
                            networkResult.data?.let {
                                _characterList.value = it
                                _characterLoading.value = false
                                Log.e("asd", "data")
                            }
                        }
                        is NetworkResult.Error -> {
                            _characterError.value = networkResult.message ?: "Error! No Data"
                            _characterLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
    }

    fun getFilterCharacters() {
        setFilterQueryMap()
        job?.cancel()
        filterQueryMap.value?.let { map ->
            job = getFilterCharacterUIModelUseCase.executeGetFilterCharacters(viewModelScope,map)
                .onEach { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Loading -> {
                            _characterLoading.value = true
                        }
                        is NetworkResult.Success -> {
                            networkResult.data?.let {
                                _characterList.value = it
                                _characterLoading.value = false
                            }
                        }
                        is NetworkResult.Error -> {
                            _characterError.value = networkResult.message ?: "Error! No Data"
                            _characterLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
        }

    }

}