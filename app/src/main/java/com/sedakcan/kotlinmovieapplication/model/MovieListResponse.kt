package com.sedakcan.kotlinmovieapplication.model

import com.google.gson.annotations.SerializedName


data class MovieListResponse(

    @SerializedName("dates")
    var dates: Dates? = Dates(),
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var movieList: ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)