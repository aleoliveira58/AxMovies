package com.example.axmovies.features.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axmovies.base.BaseViewModel
import com.example.axmovies.features.home.useCase.HomeUseCase
import com.example.axmovies.model.Popular
import com.example.axmovies.model.Result
import com.example.axmovies.utils.ResponseApi
import kotlinx.coroutines.launch


// ViewModel = trabalha os dados (data class )

class HomeViewModel : BaseViewModel() {


    // Tendo acesso ao useCse - 3 passo
    private val homeUseCase = HomeUseCase()

    // NowPlayingMovies
    // Recebe o livedata "Result" pq tratou dados e estava esperando uma lista de Result
    private val _onSucessNowPlaying: MutableLiveData<List<Result>> =
        MutableLiveData()
    val onSucessNowPlaying: LiveData<List<Result>>
        get() = _onSucessNowPlaying

    private val _onErrorNowPlaying: MutableLiveData<Int> =
        MutableLiveData()
    val onErrorNowPlaying: LiveData<Int>
        get() = _onErrorNowPlaying


    //Mandar um erro personalizado : uma menssagem, abrir outra tela etc..
    private val _onCustomErrorNowPlaying: MutableLiveData<Boolean> =
        MutableLiveData()
    val onCustomErrorNowPlaying: LiveData<Boolean>
        get() = _onCustomErrorNowPlaying


    //PopularMovies
    // Recebe livedata "Popular" pq não tratou nenhum dados
    private val _onSucessPopular: MutableLiveData<Popular> =
        MutableLiveData()
    val onSucessPopular: LiveData<Popular>
        get() = _onSucessPopular

    private val _onErrorPopular: MutableLiveData<Int> =
        MutableLiveData()
    val onErrorPopular: LiveData<Int>
        get() = _onErrorPopular

    // Usar sempre postValeu para não travar thred do usuario
    // Ir da viewModel para useCase - 4 passo
    // CoroutineScope não faz travar a trad do usuario mesmo matando a home
    fun getNowPlayingMovies() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getNowPlayingMovies() },
                onSuccess = {
                    val result = it as? List<*>
                    _onSucessNowPlaying.postValue(
                        result?.filterIsInstance<Result>()
                    )
                },
                onError = { _onCustomErrorNowPlaying.postValue(true)}
            )
        }
    }


    fun getPopularMovies() {
        viewModelScope.launch {
            callApi(
                suspend {
                    homeUseCase.getPopularMovies()
                },
                onSuccess = {
                    _onSucessPopular.postValue(
                        it as Popular
                    )
                }
            )

        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            callApi(
                suspend {
                    homeUseCase.getMovieById(id)
                },
                onSuccess = {
                     it
                }
            )
        }

    }
}