package com.caneryilmazapps.beinmovieapp.adapters.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caneryilmazapps.beinmovieapp.R
import com.caneryilmazapps.beinmovieapp.data.models.response.Movie
import com.caneryilmazapps.beinmovieapp.utils.Constants
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieRecyclerViewAdapter :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieAdapterViewHolder>() {

    class MovieAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapterViewHolder {
        return MovieAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        val movieItem = differ.currentList[position]
        holder.itemView.apply {
            movie_list_item_movie_name.text = movieItem.title
            Glide.with(this)
                .load("${Constants.moviePosterPath}${movieItem.poster_path}")
                .into(movie_list_item_movie_image)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(movieItem)
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }
}