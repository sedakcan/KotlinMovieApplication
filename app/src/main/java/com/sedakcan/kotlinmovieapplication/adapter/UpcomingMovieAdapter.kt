package com.sedakcan.kotlinmovieapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sedakcan.kotlinmovieapplication.model.Movie
import com.sedakcan.kotlinmovieapplication.R
import com.sedakcan.kotlinmovieapplication.databinding.ItemMovieBinding
import com.sedakcan.kotlinmovieapplication.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_movie.view.*


class UpcomingMovieAdapter(val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<UpcomingMovieAdapter.MovieViewHolder>(), MovieClickListener {

    class MovieViewHolder(var view: ItemMovieBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovieList(newMovieList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    override fun onMovieClicked(v: View) {
        val upcomingMovieId = v.tvUpcomingMovieIdText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToMovieFragment()
        action.movieId = upcomingMovieId
        Navigation.findNavController(v).navigate(action)
    }
}