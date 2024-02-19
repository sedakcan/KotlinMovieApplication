package com.sedakcan.kotlinmovieapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedakcan.kotlinmovieapplication.model.Movie
import com.sedakcan.kotlinmovieapplication.service.MovieAPIService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.sedakcan.kotlinmovieapplication.model.MovieListResponse
import com.sedakcan.kotlinmovieapplication.model.NowPlayingMovie

class FeedViewModel() : ViewModel() {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()


    val upcomingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies = MutableLiveData<List<NowPlayingMovie>>()


    fun refreshData() {
        getDataFromAPI()

        getSliderData()
    }

    private fun getDataFromAPI() {

        disposable.add(
            movieApiService.getUpcomingMoviesData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieListResponse>() {
                    override fun onSuccess(t: MovieListResponse) {
                        upcomingMovies.value = t.movieList
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getSliderData() {

        disposable.add(
            movieApiService.getNowPlayingMoviesData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieListResponse>() {
                    override fun onSuccess(t: MovieListResponse) {

                        val nowPlayingMovieList = arrayListOf<NowPlayingMovie>()

                        for (movie in t.movieList.take(5)) {
                            val nowPlayingMovie = NowPlayingMovie(movie.id!!, movie.backdropPath.toString(),movie.title.toString(),movie.overview.toString())
                            nowPlayingMovieList.add(nowPlayingMovie)
                        }

                        nowPlayingMovies.value = nowPlayingMovieList
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}


