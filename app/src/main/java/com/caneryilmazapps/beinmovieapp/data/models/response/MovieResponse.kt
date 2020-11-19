package com.caneryilmazapps.beinmovieapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val total_results: Int,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("results")
    val results: List<Movie>
) : Serializable {
    override fun toString(): String {
        return "MovieResponse(page=$page, total_results=$total_results, total_pages=$total_pages, results=$results)"
    }
}

data class Movie(

    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_count")
    val vote_count: Int,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val release_date: String
) : Serializable {
    override fun toString(): String {
        return "Movie(popularity=$popularity, vote_count=$vote_count, video=$video, poster_path='$poster_path', id=$id, adult=$adult, backdrop_path='$backdrop_path', original_language='$original_language', original_title='$original_title', genre_ids=$genre_ids, title='$title', vote_average=$vote_average, overview='$overview', release_date='$release_date')"
    }
}