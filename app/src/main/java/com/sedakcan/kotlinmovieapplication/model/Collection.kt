package com.sedakcan.kotlinmovieapplication.model

import com.google.gson.annotations.SerializedName


data class Collection(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("poster_path")
    var poster_path: String? = null,
    @SerializedName("backdrop_path")
    var backdrop_path: String? = null
)