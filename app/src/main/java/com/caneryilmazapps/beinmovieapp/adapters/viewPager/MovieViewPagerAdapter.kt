package com.caneryilmazapps.beinmovieapp.adapters.viewPager

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.caneryilmazapps.beinmovieapp.R
import com.caneryilmazapps.beinmovieapp.data.models.response.Genre

class MovieViewPagerAdapter(
    private val activity: Activity,
    private val genreList: List<Genre>,
    private val listenerMovieView: MovieViewPagerListener
) :
    PagerAdapter() {

    override fun getCount(): Int {
        return genreList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RecyclerView)
    }

    override fun getPageWidth(position: Int): Float {
        return 1f
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return genreList[position].name
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater =
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        lateinit var root: RecyclerView
        root = layoutInflater.inflate(
            R.layout.pager_movie_list_recycler_view,
            container,
            false
        ) as RecyclerView

        MovieViewPagerViewHolder(root, listenerMovieView)

        container.addView(root)
        return root
    }
}
