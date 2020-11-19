package com.caneryilmazapps.beinmovieapp.di

import com.caneryilmazapps.beinmovieapp.data.remote.MovieApi
import com.caneryilmazapps.beinmovieapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object MovieModule {

    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository = MovieRepository(movieApi)
}