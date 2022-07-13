package com.example.axmovies.api

import com.example.axmovies.model.Movie
import com.example.axmovies.model.NowPlaying
import com.example.axmovies.model.Popular
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TMDBApi {


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>


    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<Popular>


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