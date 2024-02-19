package com.sedakcan.kotlinmovieapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedakcan.kotlinmovieapplication.model.MovieDetailResponse
import com.sedakcan.kotlinmovieapplication.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieViewModel:ViewModel() {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    val movieLiveData=MutableLiveData<MovieDetailResponse>()

    fun getData(uuid:Int)
    {
        disposable.add(
            movieApiService.getMoviesDetailData(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>() {
                    override fun onSuccess(t: MovieDetailResponse) {
                        movieLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }
}