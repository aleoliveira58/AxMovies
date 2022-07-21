package com.example.axmovies.features.home.repository

import android.app.Application
import com.example.axmovies.api.ApiService
import com.example.axmovies.base.BaseRepository
import com.example.axmovies.database.AxMoviesDataBase
import com.example.axmovies.model.*
import com.example.axmovies.modeldb.Movie
import com.example.axmovies.utils.ResponseApi


// Os repository tem que herdar de BaseRepository pq e onde esta invocando a chamada de api

class HomeRepository( val application: Application) : BaseRepository() {

    suspend fun getNowPlayingMovies(page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies(page)
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

    suspend fun getGenre(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getGenre()
        }
    }


    // Chamada do bancos de dados
    suspend fun saveGenreDateBase(genres: List<Genre>?) {
        genres?.let{ genresApi ->
            val genresDb: MutableList<com.example.axmovies.modeldb.Genre> = mutableListOf()
            genresApi.forEach {
                genresDb.add(it.toGenreDb())
            }
            AxMoviesDataBase
                .getDatabase(application)
                .genreDao()
                .insertAllGenres(genresDb)
        }


    }

    suspend fun saveMoviesDb(movies: List<Result>) {
        movies.let { moviesApi ->
            val moviesDb: MutableList<Movie> = mutableListOf()
            moviesApi.forEach {
                moviesDb.add(it.toMovieDb())
            }
            AxMoviesDataBase
                .getDatabase(application)
                .movieDao()
                .insertAllMovies(moviesDb)
        }
    }
}