package com.betulantep.rickandmorty.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.betulantep.rickandmorty.data.entities.character.toCharacterUIModel
import com.betulantep.rickandmorty.domain.repo.AppRepository
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel
import com.betulantep.rickandmorty.utils.Constants.FIRST_PAGE_INDEX
import com.betulantep.rickandmorty.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCharacterUIModelUseCase @Inject constructor(var repo: AppRepository) {
    fun executeGetCharacters(coroutineScope: CoroutineScope): Flow<NetworkResult<PagingData<CharacterUIModel>>> =
        flow {
            try {
                emit(NetworkResult.Loading())
                val response = repo.getCharactersNetworkResult(FIRST_PAGE_INDEX)
                if(response.characters.isNullOrEmpty()){
                    emit(NetworkResult.Error(message = "No Rick And Morty Character"))
                }else{
                    repo.getCharacters().cachedIn(coroutineScope).collect {
                        emit(NetworkResult.Success(it.map {
                                character -> character.toCharacterUIModel()
                        }))
                    }
                }

            } catch (e: HttpException) {
                emit(NetworkResult.Error(message = e.localizedMessage ?: "Error!"))
            } catch (e: IOException) {
                emit(NetworkResult.Error(message = "Check Your Internet"))
            }
        }
}