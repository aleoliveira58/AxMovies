package com.example.axmovies.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


import com.example.axmovies.modeldb.Movie
import com.example.axmovies.modeldb.MovieWithGenre

@Dao
interface MovieDao {


    @Transaction
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieWithGenre>

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun loadMovieById(movieId: Int): List<Movie>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(movieList: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovies(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

}