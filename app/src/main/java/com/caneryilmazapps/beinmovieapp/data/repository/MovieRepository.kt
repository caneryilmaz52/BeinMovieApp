package com.caneryilmazapps.beinmovieapp.data.repository

import com.caneryilmazapps.beinmovieapp.data.remote.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieGenre() = movieApi.getMovieGenre()

    suspend fun getMoviesByGenre(genreId: String) = movieApi.getMoviesByGenre(genreId)
}