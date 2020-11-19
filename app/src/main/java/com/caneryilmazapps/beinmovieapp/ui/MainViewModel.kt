package com.caneryilmazapps.beinmovieapp.ui

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caneryilmazapps.beinmovieapp.data.models.response.GenreResponse
import com.caneryilmazapps.beinmovieapp.data.models.response.MovieResponse
import com.caneryilmazapps.beinmovieapp.data.repository.MovieRepository
import com.caneryilmazapps.beinmovieapp.utils.NetworkControl
import com.caneryilmazapps.beinmovieapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var genreList: MutableLiveData<Resource<GenreResponse>> = MutableLiveData()

    var movieList: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

    fun getGenreList(context: Context) = viewModelScope.launch {
        safeGetGenreListCall(context)
    }

    fun getMovieList(context: Context, genreId: String) = viewModelScope.launch {
        safeGetMovieListCall(context, genreId)
    }

    private suspend fun safeGetGenreListCall(context: Context) {
        genreList.postValue(Resource.Loading())
        try {
            if (NetworkControl.hasInternetConnection(context)) {
                val response = movieRepository.getMovieGenre()
                genreList.postValue(handleGenreListResponse(response))
            } else {
                genreList.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    genreList.postValue(Resource.Error("Network error"))
                else ->
                    genreList.postValue(
                        Resource.Error("An error occurred, please try again")
                    )
            }
        }
    }

    private fun handleGenreListResponse(response: Response<GenreResponse>): Resource<GenreResponse> {
        if (response.isSuccessful && response.body()?.genre?.isNotEmpty()!!) {
            response.body()?.let {
                return Resource.Success(it)
            }
        } else {
            response.errorBody()?.let {
                return Resource.Error(it.string())
            }
        }
        return Resource.Error("An error occurred, please try again")
    }

    private suspend fun safeGetMovieListCall(context: Context, genreId: String) {
        movieList.postValue(Resource.Loading())
        try {
            if (NetworkControl.hasInternetConnection(context)) {
                val response = movieRepository.getMoviesByGenre(genreId)
                movieList.postValue(handleMovieListResponse(response))
            } else {
                movieList.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    movieList.postValue(Resource.Error("Network error"))
                else ->
                    movieList.postValue(
                        Resource.Error("An error occurred, please try again")
                    )
            }
        }
    }

    private fun handleMovieListResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful && response.body()?.results?.isNotEmpty()!!) {
            response.body()?.let {
                return Resource.Success(it)
            }
        } else {
            response.errorBody()?.let {
                return Resource.Error(it.string())
            }
        }
        return Resource.Error("An error occurred, please try again")
    }

}