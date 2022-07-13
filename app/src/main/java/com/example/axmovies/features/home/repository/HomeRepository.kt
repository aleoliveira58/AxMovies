package com.example.axmovies.features.home.repository

import com.example.axmovies.api.ApiService
import com.example.axmovies.base.BaseRepository
import com.example.axmovies.utils.ResponseApi


// Os repository tem que herdar de BaseRepository pq e onde esta invocando a chamada de api

class HomeRepository : BaseRepository() {

    suspend fun getNowPlayingMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies()
        }
    }

    suspend fun getPopularMovies(): ResponseApi {
       return safeApiCall {
           ApiService.tmdbApi.getPopularMovies()
       }
    }

    suspend fun getMovieById(id: Int): ResponseApi {
        return  safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }

    }
}