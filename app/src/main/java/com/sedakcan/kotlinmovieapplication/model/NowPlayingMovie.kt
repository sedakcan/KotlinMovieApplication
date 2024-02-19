package com.sedakcan.kotlinmovieapplication.model

import javax.inject.Inject

class NowPlayingMovie @Inject
 constructor(
    val id : Int,
    val url : String,
    val header : String,
    val summary : String,
)