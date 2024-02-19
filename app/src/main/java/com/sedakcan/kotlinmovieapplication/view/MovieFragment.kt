package com.sedakcan.kotlinmovieapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sedakcan.kotlinmovieapplication.R
import com.sedakcan.kotlinmovieapplication.databinding.FragmentMovieBinding
import com.sedakcan.kotlinmovieapplication.util.downloadFromUrl
import com.sedakcan.kotlinmovieapplication.util.formatDate
import com.sedakcan.kotlinmovieapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private var movieId = 0
    private lateinit var dataBinding: FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movieId = MovieFragmentArgs.fromBundle(it).movieId
        }

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getData(movieId)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                dataBinding.selectedMovie = movie
            }
        })
    }
}