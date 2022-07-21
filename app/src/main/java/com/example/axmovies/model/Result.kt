package com.example.axmovies.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.axmovies.modeldb.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize



@Parcelize
data class Result(
    val genre_ids: List<Int>?,
    val id: Int?,
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    val original_language: String?,
    val original_title: String? ,
    val overview: String? ,
    val popularity: Double? ,
    var poster_path: String?,
    val release_date: String? ,
    val title: String? ,
    val video: Boolean?,
    val vote_average: Double? ,
    val vote_count: Int?,

) : Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Result> =
            object : DiffUtil.ItemCallback<Result>() {
                override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }




}


fun Result.toMovieDb(): com.example.axmovies.modeldb.Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}