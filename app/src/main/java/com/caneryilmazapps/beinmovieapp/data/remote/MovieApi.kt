package com.caneryilmazapps.beinmovieapp.data.remote

import com.caneryilmazapps.beinmovieapp.data.models.response.GenreResponse
import com.caneryilmazapps.beinmovieapp.data.models.response.MovieResponse
import com.caneryilmazapps.beinmovieapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(Constants.getMovieGenre)
    suspend fun getMovieGenre(): Response<GenreResponse>

    @GET(Constants.getMoviesByGenre)
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: String): Response<MovieResponse>
}