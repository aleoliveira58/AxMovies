package com.example.axmovies.features.movieDetail.useCase

import com.example.axmovies.extensions.getFullImageUrl
import com.example.axmovies.features.movieDetail.repository.MovieDetailRepository
import com.example.axmovies.model.Movie
import com.example.axmovies.utils.ResponseApi

class MovieDetailUseCase {

    private var movieDetailRepository = MovieDetailRepository()



    suspend fun getMovieById(movieId: Int): ResponseApi {
        return when(val responseApi = movieDetailRepository.getMovieId(movieId)) {
            is ResponseApi.Success -> {
                val movie = responseApi.data as? Movie
                movie?.backdrop_path = movie?.backdrop_path?.getFullImageUrl()
                return ResponseApi.Success(movie)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
        }


}