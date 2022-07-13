package com.example.axmovies.features.home.useCase

import com.example.axmovies.api.ApiService
import com.example.axmovies.extensions.getFullImageUrl
import com.example.axmovies.features.home.repository.HomeRepository
import com.example.axmovies.model.NowPlaying
import com.example.axmovies.utils.ResponseApi

// A model (useCase) onde de fato faz a chamada de api
//Onde faz a regra de negocio (model) -> pode ser chama de useCase

class HomeUseCase {

    // Ter acesso ao homeRepository  - passo 5
    private val homeRepository = HomeRepository()

    // Chamando a useCase para o homeRepository
    // De fato fazendo a chamada de api  - passo 6
    // Para user o safeApiCall  a função tem que estar e suspend pq ele está em suspend
    suspend fun getNowPlayingMovies(): ResponseApi {
        return when (val responseApi = homeRepository.getNowPlayingMovies()) {
            is ResponseApi.Success -> {
                // Tratando o url da image
                // map retorna uma litta com dados alterados
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map {
                    it.backdrop_path = it.backdrop_path.getFullImageUrl()
                    it.poster_path = it.poster_path.getFullImageUrl()
                    it
                }
                ResponseApi.Success(result)

            }
            is ResponseApi.Error -> {
                  responseApi
            }
        }
    }

    suspend fun getPopularMovies(): ResponseApi {
       return homeRepository.getPopularMovies()
    }

    suspend fun getMovieById(id : Int): ResponseApi {
        return homeRepository.getMovieById(id)
    }
}