package com.betulantep.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.betulantep.rickandmorty.data.repo.AppRepository
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import com.betulantep.rickandmorty.domain.usecase.GetCharacterUIModelUseCase
import com.betulantep.rickandmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    var repo: AppRepository,
    var getCharacterUIModelUseCase: GetCharacterUIModelUseCase
) : ViewModel() {

    private val _characterList = MutableStateFlow<PagingData<CharacterUIModel>>(PagingData.empty())
    val characterList: StateFlow<PagingData<CharacterUIModel>> = _characterList

    private val _characterLoading = MutableStateFlow(false)
    val characterLoading : StateFlow<Boolean> = _characterLoading

    private val _characterError = MutableStateFlow("")
    val characterError : StateFlow<String> = _characterError

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharacterUIModelUseCase.executeGetCharacters(viewModelScope).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        _characterLoading.value = true
                        Log.e("asd","loading")
                    }
                    is NetworkResult.Success -> {
                        networkResult.data?.let {
                            _characterList.value = it
                            _characterLoading.value = false
                            Log.e("asd","data")
                        }
                    }
                    is NetworkResult.Error -> {
                        _characterError.value = networkResult.message ?: "Error! No Data"
                        _characterLoading.value = false
                        Log.e("asd",_characterError.value)
                    }
                }
            }
        }
    }
}