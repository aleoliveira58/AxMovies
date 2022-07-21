package com.example.axmovies.modeldb

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenre(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId"
    )
    val genres: List<Genre>
)
