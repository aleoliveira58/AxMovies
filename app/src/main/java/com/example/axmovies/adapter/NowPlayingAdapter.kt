package com.example.axmovies.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.axmovies.R
import com.example.axmovies.databinding.NowPlayingItemBinding
import com.example.axmovies.model.Result

class NowPlayingAdapter (
    private val nowPlayingList: List<Result>,
    private val onClickListener: (movie: Result) -> Unit
) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>(){


    class NowPlayingViewHolder(
        private val binding: NowPlayingItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(
            movie : Result,
            onClickListener: (movie: Result) -> Unit
        ){
            with(binding){
                tvWatchTitle.text = movie.title
                tvWatchYear.text = movie.release_date
                cvNowPlayingItem.setOnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val binding = NowPlayingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NowPlayingAdapter.NowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.bind(nowPlayingList[position], onClickListener)
    }

    override fun getItemCount() = nowPlayingList.size




}