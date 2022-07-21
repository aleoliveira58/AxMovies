package com.example.axmovies.api

import com.example.axmovies.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface TMDBApi {


    //@Query muda depois das linguagem no postman (filtra a api)
    //@Path muda antes da chave key no url

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int):
            Response<NowPlaying>


    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<Popular>

    @GET("genre/movie/list")
    suspend fun getGenre(): Response<GenreInfo>


    // Fazer busca dinamica ex: quando clicar ne um filme vai ter as informações dele
    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): Response<Movie>


    @POST("movie/save")
    suspend fun saveMovie(
        @Body movie: Movie
    ): Response<ResponseBody>
}