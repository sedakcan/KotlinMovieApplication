package com.sedakcan.kotlinmovieapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.sedakcan.kotlinmovieapplication.R
import com.sedakcan.kotlinmovieapplication.adapter.NowPlayingMovieAdapter
import com.sedakcan.kotlinmovieapplication.adapter.UpcomingMovieAdapter
import com.sedakcan.kotlinmovieapplication.databinding.FragmentFeedBinding
import com.sedakcan.kotlinmovieapplication.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_feed.*

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply { setMargins(8, 0, 8, 0) }

    val nowPlayingMovieAdapter = NowPlayingMovieAdapter()
    val upcomingMovieAdapter = UpcomingMovieAdapter(arrayListOf())
    private lateinit var dataBinding : FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        viewpager2.adapter = nowPlayingMovieAdapter

        rvMovieList.layoutManager = LinearLayoutManager(context)
        rvMovieList.adapter = upcomingMovieAdapter

        swipeRefreshLayout.setOnRefreshListener {
            rvMovieList.visibility = View.GONE
            viewModel.refreshData()

            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData() {

        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->
            upcomingMovies?.let {
                rvMovieList.visibility = View.VISIBLE
                upcomingMovieAdapter.updateMovieList(upcomingMovies)
            }
        })


        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { nowPlayingMovies ->
            nowPlayingMovies?.let {

                nowPlayingMovieAdapter.submitList(nowPlayingMovies)

                val dotsImage = Array(nowPlayingMovies.size) { ImageView(context) }
                slideDotLL.removeAllViews()

                dotsImage.forEach {
                    it.setImageResource(
                        R.drawable.non_active_dot
                    )
                    slideDotLL.addView(it, params)
                }

                dotsImage[0].setImageResource(R.drawable.active_dot)

                pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        dotsImage.mapIndexed { index, imageView ->
                            if (position == index) {
                                imageView.setImageResource(
                                    R.drawable.active_dot
                                )
                            } else {
                                imageView.setImageResource(R.drawable.non_active_dot)
                            }
                        }
                        super.onPageSelected(position)
                    }
                }
                viewpager2.registerOnPageChangeCallback(pageChangeListener)
            }
        })


    }


}