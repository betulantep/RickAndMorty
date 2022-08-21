package com.betulantep.rickandmorty.data.di

import com.betulantep.rickandmorty.domain.repo.AppRepository
import com.betulantep.rickandmorty.data.repo.AppRepositoryImpl
import com.betulantep.rickandmorty.data.retrofit.AppRemoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppRepository(remoteDao: AppRemoteDao) = AppRepositoryImpl(remoteDao) as AppRepository
    /*fun provideAppRepository(remoteDao: AppRemoteDao) : AppRepository{
        return AppRepositoryImpl(remoteDao)
    }*/
}