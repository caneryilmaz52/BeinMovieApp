package com.caneryilmazapps.beinmovieapp.ui.fragments.movieDetail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.caneryilmazapps.beinmovieapp.R
import com.caneryilmazapps.beinmovieapp.ui.MainActivity
import com.caneryilmazapps.beinmovieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var movieBackImage: ImageView
    private lateinit var moviePosterImage: ImageView
    private lateinit var movieRate: TextView
    private lateinit var movieReleaseInfo: TextView
    private lateinit var movieGenre: TextView
    private lateinit var movieOverview: TextView

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieBackImage = view.findViewById(R.id.fr_movie_detail_back_image)
        moviePosterImage = view.findViewById(R.id.fr_movie_detail_poster_image)
        movieRate = view.findViewById(R.id.fr_movie_detail_rating)
        movieReleaseInfo = view.findViewById(R.id.fr_movie_detail_release_info)
        movieGenre = view.findViewById(R.id.fr_movie_detail_genre_info)
        movieOverview = view.findViewById(R.id.fr_movie_detail_overview)

        movieOverview.movementMethod = ScrollingMovementMethod()

        setMovieDetailData()

        (requireActivity() as MainActivity).supportActionBar?.title = args.movieItem.title
    }

    private fun setMovieDetailData() {
        val movie = args.movieItem
        val genre = args.genreString

        Glide.with(requireActivity()).load("${Constants.moviePosterPath}${movie.backdrop_path}")
            .into(movieBackImage)
        Glide.with(requireActivity()).load("${Constants.moviePosterPath}${movie.poster_path}")
            .into(moviePosterImage)

        movieRate.text = "Vote: ${movie.vote_average} (${movie.vote_count})"
        movieReleaseInfo.text = "Year: ${movie.release_date.substring(0, 4)}"
        movieGenre.text = genre.removeRange(genre.length - 2, genre.length)

        movieOverview.text = movie.overview
    }
}