package com.caneryilmazapps.beinmovieapp.utils

class Constants {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val moviePosterPath = "https://image.tmdb.org/t/p/w185"
        const val getMovieGenre = "genre/movie/list?api_key=3bb3e67969473d0cb4a48a0dd61af747&language=en-US"
        const val getMoviesByGenre = "discover/movie?api_key=3bb3e67969473d0cb4a48a0dd61af747&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&"
    }
}