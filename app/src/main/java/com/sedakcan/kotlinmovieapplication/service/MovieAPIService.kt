package com.sedakcan.kotlinmovieapplication.service

import com.sedakcan.kotlinmovieapplication.model.MovieDetailResponse
import com.sedakcan.kotlinmovieapplication.model.MovieListResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MovieAPIService @Inject constructor() {

    private val BASE_URL = "https://api.themoviedb.org/"
    private val API_KEY = "482cfb1d7653f5cc1d8480187804b170"
    private val TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ODJjZmIxZDc2NTNmNWNjMWQ4NDgwMTg3ODA0YjE3MCIsInN1YiI6IjY1NmM2YTI1MDg1OWI0MDBmZjc1OWM1OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2SnZGrWrxczWVy_59u-EenzMIhcj_icqxWJAcR9-vTs"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getNowPlayingMoviesData(): Single<MovieListResponse> {
        return api.getNowPlayingMovies(API_KEY, TOKEN)
    }

    fun getMoviesDetailData(id:Int): Single<MovieDetailResponse> {
        return api.getMovieDetail(id,API_KEY, TOKEN)
    }

    fun getUpcomingMoviesData(): Single<MovieListResponse> {
        return api.getUpcomingMovies(API_KEY, 1, TOKEN)
    }
}