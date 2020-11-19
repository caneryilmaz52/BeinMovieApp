package com.caneryilmazapps.beinmovieapp.di

import com.caneryilmazapps.beinmovieapp.data.repository.MovieRepository
import com.caneryilmazapps.beinmovieapp.ui.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {
    @Provides
    fun provideMainViewModel(movieRepository: MovieRepository): MainViewModel =
        MainViewModel(movieRepository)
}