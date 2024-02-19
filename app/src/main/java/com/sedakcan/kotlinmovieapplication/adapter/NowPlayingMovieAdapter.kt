package com.sedakcan.kotlinmovieapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedakcan.kotlinmovieapplication.R
import com.sedakcan.kotlinmovieapplication.databinding.ImageItemLayoutBinding
import com.sedakcan.kotlinmovieapplication.model.NowPlayingMovie
import com.sedakcan.kotlinmovieapplication.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.image_item_layout.view.*

class NowPlayingMovieAdapter :
    ListAdapter<NowPlayingMovie, NowPlayingMovieAdapter.ViewHolder>(DiffCallback()), MovieClickListener {

    class DiffCallback : DiffUtil.ItemCallback<NowPlayingMovie>() {
        override fun areItemsTheSame(
            oldItem: NowPlayingMovie,
            newItem: NowPlayingMovie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NowPlayingMovie,
            newItem: NowPlayingMovie
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(var view: ImageItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            DataBindingUtil.inflate<ImageItemLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.image_item_layout,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.nowPlayingMovie = getItem(position)
        holder.view.listener = this
    }

    override fun onMovieClicked(v: View) {
        val nowPlayingMovieId = v.tvnowPlayingMovieIdText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToMovieFragment()
        action.movieId = nowPlayingMovieId
        Navigation.findNavController(v).navigate(action)
    }
}