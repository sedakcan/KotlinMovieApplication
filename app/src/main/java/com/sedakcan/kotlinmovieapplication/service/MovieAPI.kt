package com.sedakcan.kotlinmovieapplication.service

import com.sedakcan.kotlinmovieapplication.model.MovieDetailResponse
import com.sedakcan.kotlinmovieapplication.model.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {


    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") api_key: String,
        @Header("Authorization") token: String
    ): Single<MovieListResponse>

    @GET("3/movie/{id}")
    fun getMovieDetail(
        @Path(value = "id") id: Int,
        @Query("api_key") api_key: String,
        @Header("Authorization") token: String
    ): Single<MovieDetailResponse>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): Single<MovieListResponse>


}