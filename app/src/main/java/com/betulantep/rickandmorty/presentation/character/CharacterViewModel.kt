package com.betulantep.rickandmorty.presentation.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.betulantep.rickandmorty.domain.repo.AppRepository
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import com.betulantep.rickandmorty.domain.usecase.GetCharacterUIModelUseCase
import com.betulantep.rickandmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    var repo: AppRepository,
    var getCharacterUIModelUseCase: GetCharacterUIModelUseCase
) : ViewModel() {

    private val _characterList = MutableStateFlow<PagingData<CharacterUIModel>>(PagingData.empty())
    val characterList: StateFlow<PagingData<CharacterUIModel>> = _characterList

    private val _characterLoading = MutableStateFlow(false)
    val characterLoading: StateFlow<Boolean> = _characterLoading

    private val _characterError = MutableStateFlow("")
    val characterError: StateFlow<String> = _characterError

    private var job: Job? = null

    init {
        getCharacters()
    }

   /* fun characterItemClick() :{

    }*/

    private fun getCharacters() {
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
                        Log.e("asd", _characterError.value)
                    }
                }
            }.launchIn(viewModelScope)
    }
}