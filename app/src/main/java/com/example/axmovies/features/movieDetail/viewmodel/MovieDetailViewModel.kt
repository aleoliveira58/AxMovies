package com.example.axmovies.features.movieDetail.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.axmovies.base.BaseViewModel
import com.example.axmovies.features.movieDetail.useCase.MovieDetailUseCase
import com.example.axmovies.model.Movie
import com.example.axmovies.model.Result
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application): BaseViewModel(application) {

    private var movieDetailUseCase = MovieDetailUseCase()

    private val _onSucessMovieById: MutableLiveData<Movie> =
        MutableLiveData()
    val onSucessMovieById: LiveData<Movie>
        get() = _onSucessMovieById

    private val _onErrorMovieById: MutableLiveData<Int> =
        MutableLiveData()
    val onErrorMovieById: LiveData<Int>
        get() = _onErrorMovieById



    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            callApi(
                suspend { movieDetailUseCase.getMovieById(movieId) },
                onSuccess = {
                    _onSucessMovieById.postValue(it as? Movie)
                }
            )


        }

    }
}


