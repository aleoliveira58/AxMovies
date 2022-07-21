package com.example.axmovies.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.axmovies.R
import com.example.axmovies.databinding.NowPlayingItemBinding
import com.example.axmovies.model.Result

class NowPlayingAdapter (
    private val onClickListener: (movie: Result) -> Unit
) : PagedListAdapter<Result,NowPlayingAdapter.NowPlayingViewHolder>(Result.DIFF_CALLBACK){


    class NowPlayingViewHolder(
        private val binding: NowPlayingItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(
            movie : Result?,
            onClickListener: (movie: Result) -> Unit
        ){
            with(binding){
                movie?.let {

                    tvWatchTitle.text = movie.title
                    tvWatchYear.text = movie.release_date
                    textView.setOnClickListener {
                        onClickListener(movie)
                    }
                    Glide
                        .with(itemView.context)
                        .load(movie.poster_path)
                        .placeholder(R.drawable.no_image_available)
                        .into(ivWatchImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val binding = NowPlayingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NowPlayingAdapter.NowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }






}