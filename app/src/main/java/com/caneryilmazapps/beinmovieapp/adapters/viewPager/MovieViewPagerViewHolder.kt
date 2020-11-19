package com.caneryilmazapps.beinmovieapp.adapters.viewPager

import androidx.recyclerview.widget.RecyclerView

class MovieViewPagerViewHolder(
    recyclerView: RecyclerView,
    listenerMovieView: MovieViewPagerListener
) : RecyclerView.ViewHolder(recyclerView) {

    init {
        listenerMovieView.sendRecyclerView(recyclerView)
    }

}