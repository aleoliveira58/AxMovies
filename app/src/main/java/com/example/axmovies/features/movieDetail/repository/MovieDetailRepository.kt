package com.example.axmovies.features.movieDetail.repository

import com.example.axmovies.api.ApiService
import com.example.axmovies.base.BaseRepository
import com.example.axmovies.utils.ResponseApi

class MovieDetailRepository: BaseRepository() {


    suspend fun getMovieId(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(movieId)
        }

    }
}