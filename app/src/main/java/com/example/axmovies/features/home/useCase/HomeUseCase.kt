package com.example.axmovies.features.home.useCase

import android.app.Application
import com.example.axmovies.extensions.getFullImageUrl
import com.example.axmovies.features.home.repository.HomeRepository
import com.example.axmovies.model.GenreInfo
import com.example.axmovies.model.NowPlaying
import com.example.axmovies.model.Result
import com.example.axmovies.utils.ConstantApp.Home.FIRST_PAGE
import com.example.axmovies.utils.ResponseApi

// A model (useCase) onde de fato faz a chamada de api
//Onde faz a regra de negocio (model) -> pode ser chama de useCase

class HomeUseCase(
    private val application: Application
) {

    // Ter acesso ao homeRepository  - passo 5
    private val homeRepository = HomeRepository(application)

    // Chamando a useCase para o homeRepository
    // De fato fazendo a chamada de api  - passo 6
    // Para user o safeApiCall  a função tem que estar e suspend pq ele está em suspend
    suspend fun getNowPlayingMovies(): ResponseApi {
        return when (val responseApi = homeRepository.getNowPlayingMovies(FIRST_PAGE)) {
            is ResponseApi.Success -> {
                // Tratando o url da image
                // map retorna uma litta com dados alterados
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map {
                    it.backdropPath = it.backdropPath?.getFullImageUrl()
                    it.poster_path = it.poster_path?.getFullImageUrl()
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



    fun setupMoviesList(list: NowPlaying?): List<Result> {
        return list?.results?.map {
            it.backdropPath = it.backdropPath?.getFullImageUrl()
            it.poster_path = it.poster_path?.getFullImageUrl()
            it
        } ?: listOf()
    }

    suspend fun getGenre(): ResponseApi {
        return when(val response  =  homeRepository.getGenre()){
            is ResponseApi.Success -> {
                val genreInfo = response.data as? GenreInfo
                homeRepository.saveGenreDateBase(genreInfo?.genres)
                response
            }
            is ResponseApi.Error -> {
                response
            }
        }
    }

    fun saveMoviesDb(movies: List<Result>) {
               homeRepository.saveMoviesDb(movies)
    }


}