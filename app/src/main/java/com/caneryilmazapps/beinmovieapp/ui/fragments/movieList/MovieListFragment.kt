package com.caneryilmazapps.beinmovieapp.ui.fragments.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.caneryilmazapps.beinmovieapp.R
import com.caneryilmazapps.beinmovieapp.adapters.viewPager.MovieViewPagerAdapter
import com.caneryilmazapps.beinmovieapp.adapters.recyclerView.MovieRecyclerViewAdapter
import com.caneryilmazapps.beinmovieapp.adapters.viewPager.MovieViewPagerListener
import com.caneryilmazapps.beinmovieapp.data.models.response.Genre
import com.caneryilmazapps.beinmovieapp.data.models.response.MovieResponse
import com.caneryilmazapps.beinmovieapp.ui.MainActivity
import com.caneryilmazapps.beinmovieapp.ui.MainViewModel
import com.caneryilmazapps.beinmovieapp.utils.Resource
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager

    private lateinit var genreListData: List<Genre>
    private lateinit var movieListData: MovieResponse

    private lateinit var movieRecyclerViewAdapter: MovieRecyclerViewAdapter
    private lateinit var movieViewPagerAdapter: MovieViewPagerAdapter
    private lateinit var onPageChangeListener: ViewPager.OnPageChangeListener

    private var lastSelectedTabPosition: Int = 0

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.fr_movie_list_tab_layout)
        viewPager = view.findViewById(R.id.fr_movie_list_view_pager)

        tabLayout.setupWithViewPager(viewPager)

        setupGenreListObserver()
        setupMovieListObserver()

        movieRecyclerViewAdapter = MovieRecyclerViewAdapter()
        movieRecyclerViewAdapter.setOnItemClickListener { movieItem ->
            var genreString = ""
            for (genre in genreListData) {
                for (genreId in movieItem.genre_ids)
                    if (genreId == genre.id.toInt())
                        genreString += "${genre.name}, "
            }
            val bundle = Bundle().apply {
                putSerializable("movieItem", movieItem)
                putSerializable("genreString", genreString)
            }

            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailFragment,
                bundle
            )
        }

        onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val genre = genreListData[position]

                mainViewModel.getMovieList(requireContext(), genre.id)
                lastSelectedTabPosition = position

                (requireActivity() as MainActivity).supportActionBar?.title = genre.name
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        }
    }

    private fun setupGenreListObserver() {
        mainViewModel.genreList.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    (requireActivity() as MainActivity).hideLoadingView()
                    genreListData = response.data?.genre!!
                    setupViewPager()
                }
                Resource.Status.ERROR -> {
                    (requireActivity() as MainActivity).hideLoadingView()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setCancelable(false)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            mainViewModel.getGenreList(requireContext())
                        }.show()
                }
                Resource.Status.LOADING -> {
                    (requireActivity() as MainActivity).showLoadingView()
                }
            }
            response.status = null
        })

        mainViewModel.getGenreList(requireContext())
    }

    private fun setupMovieListObserver() {
        mainViewModel.movieList.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    (requireActivity() as MainActivity).hideLoadingView()
                    movieListData = response.data!!
                    movieRecyclerViewAdapter.differ.submitList(movieListData.results)
                }
                Resource.Status.ERROR -> {
                    (requireActivity() as MainActivity).hideLoadingView()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    (requireActivity() as MainActivity).showLoadingView()
                }
            }
            response.status = null
        })
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            adapter = movieRecyclerViewAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setupViewPager() {
        movieViewPagerAdapter = MovieViewPagerAdapter(
            requireActivity(),
            genreListData,
            object : MovieViewPagerListener {
                override fun sendRecyclerView(recyclerView: RecyclerView) {
                    this@MovieListFragment.recyclerView = recyclerView
                    setupRecyclerView()
                }
            })

        viewPager.apply {
            adapter = movieViewPagerAdapter
            addOnPageChangeListener(onPageChangeListener)
        }

        onPageChangeListener.onPageSelected(lastSelectedTabPosition)
    }
}