package com.sedakcan.kotlinmovieapplication.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sedakcan.kotlinmovieapplication.R
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadBackDropUrl")
fun downloadBackDropImage(view: ImageView, url: String?) {
    if (url != null)
        if (!url.startsWith("https")) {
            view.downloadFromUrl(
                "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces$url",
                placeholderProgressBar(view.context)
            )
        } else {
            view.downloadFromUrl(
                url, placeholderProgressBar(view.context)
            )
        }
}

@BindingAdapter("android:downloadPosterUrl")
fun downloadPosterImage(view: ImageView, url: String?) {
    view.downloadFromUrl(
        "https://image.tmdb.org/t/p/w440_and_h660_face$url",
        placeholderProgressBar(view.context)
    )
}

@BindingAdapter("android:formattedDateText")
fun formatStringDate(view: TextView, dateString: String?) {
    view.formatDate(dateString)
}

fun TextView.formatDate(dateString: String?) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    if (dateString != null) {
        val date: Date = inputFormat.parse(dateString)
        this.text = outputFormat.format(date).toString()
    }
}