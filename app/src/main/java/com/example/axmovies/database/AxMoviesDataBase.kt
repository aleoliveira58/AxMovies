package com.example.axmovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.axmovies.model.Result
import com.example.axmovies.modeldb.Genre
import com.example.axmovies.modeldb.Movie

object AxMoviesDataBase {

    @Database(entities = [Movie::class , Genre::class], version = 1)
    abstract class AxMoviesRoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun genreDao(): GenreDao
    }

    fun getDatabase(context: Context) : AxMoviesRoomDatabase {
        return Room.databaseBuilder(
            context,
            AxMoviesRoomDatabase::class.java, "axMovies_db"
        ).build()
    }

}