package com.example.axmovies.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "genre")
@Parcelize
data class Genre(
    @PrimaryKey
    @ColumnInfo (name = "genreId")
    val id: Int,
    val name: String
): Parcelable