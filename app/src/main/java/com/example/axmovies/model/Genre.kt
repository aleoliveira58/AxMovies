package com.example.axmovies.model

data class Genre(
    val id: Int,
    val name: String
)

fun Genre.toGenreDb(): com.example.axmovies.modeldb.Genre{
  return com.example.axmovies.modeldb.Genre(
      id = this.id,
     name = this.name
  )
    
}