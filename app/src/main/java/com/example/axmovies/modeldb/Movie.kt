package com.example.axmovies.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity (tableName = "movie")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int?,
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    var poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,

    ) : Parcelable


