package com.example.axmovies.model

import com.google.gson.annotations.SerializedName

data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    var backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    var poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)