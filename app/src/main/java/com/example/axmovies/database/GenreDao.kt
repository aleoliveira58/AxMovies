package com.example.axmovies.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

import com.example.axmovies.modeldb.Genre


@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    suspend fun getAllGenre(): List<Genre>

    @Query("SELECT * FROM genre WHERE genreId = :genreId")
    suspend fun loadGenreById(genreId: Int): List<Genre>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllGenres(genreList: List<Genre>)

    @Insert(onConflict = REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)

}