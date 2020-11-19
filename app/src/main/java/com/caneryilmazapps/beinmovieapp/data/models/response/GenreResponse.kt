package com.caneryilmazapps.beinmovieapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GenreResponse(
    @SerializedName("genres")
    val genre: List<Genre>
) : Serializable {
    override fun toString(): String {
        return "GenreResponse(genre=$genre)"
    }
}

data class Genre(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable {
    override fun toString(): String {
        return "Genre(id='$id', name='$name')"
    }
}
