package com.betulantep.rickandmorty.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.data.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(var repo: AppRepository) : ViewModel() {
    private var characterList = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val characterListState : StateFlow<PagingData<Character>> = characterList

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            repo.getCharacters().cachedIn(viewModelScope).collect {
                characterList.value = it
            }
        }
    }
}