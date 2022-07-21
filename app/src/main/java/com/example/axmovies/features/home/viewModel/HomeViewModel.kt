package com.example.axmovies.features.home.viewModel


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.axmovies.base.BaseViewModel
import com.example.axmovies.features.home.paging.HomeDataSourceFactory
import com.example.axmovies.features.home.paging.HomePageKeyedDataSource
import com.example.axmovies.features.home.repository.HomeRepository
import com.example.axmovies.features.home.useCase.HomeUseCase
import com.example.axmovies.model.Genre
import com.example.axmovies.model.Movie
import com.example.axmovies.model.Result
import com.example.axmovies.utils.ConstantApp.Home.PAGE_SIZE
import kotlinx.coroutines.launch


// ViewModel = trabalha os dados (data class )

class HomeViewModel(application: Application) : BaseViewModel(application) {

    // Tendo acesso ao useCse - 3 passo
    private val homeUseCase = HomeUseCase(getApplication())
    private val homeRepository = HomeRepository(getApplication<Application>())

    var moviesPagedList: LiveData<PagedList<Result>>? = null
    private var watchMoviesLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        val homePageKeyedDataSource = HomePageKeyedDataSource(
            homeUseCase = homeUseCase,
            homeRepository = homeRepository
        )
        val homeDataSourceFactory =
            HomeDataSourceFactory.HomeDataSourceFactory(homePageKeyedDataSource)

        watchMoviesLiveDataSource = homeDataSourceFactory.getLiveDataSource()
        moviesPagedList = LivePagedListBuilder(homeDataSourceFactory, pagedListConfig)
            .build()

    }


 /// Codigos que usa sem Paging

//    // NowPlayingMovies
//    // Recebe o livedata "Result" pq tratou dados e estava esperando uma lista de Result
//    private val _onSucessNowPlaying: MutableLiveData<List<Result>> =
//        MutableLiveData()
//    val onSucessNowPlaying: LiveData<List<Result>>
//        get() = _onSucessNowPlaying
//
//    private val _onErrorNowPlaying: MutableLiveData<Int> =
//        MutableLiveData()
//    val onErrorNowPlaying: LiveData<Int>
//        get() = _onErrorNowPlaying
//
//
//    //Mandar um erro personalizado : uma menssagem, abrir outra tela etc..
//    private val _onCustomErrorNowPlaying: MutableLiveData<Boolean> =
//        MutableLiveData()
//    val onCustomErrorNowPlaying: LiveData<Boolean>
//        get() = _onCustomErrorNowPlaying
//
//
//    //PopularMovies
//    // Recebe livedata "Popular" pq não tratou nenhum dados
//    private val _onSucessPopular: MutableLiveData<Popular> =
//        MutableLiveData()
//    val onSucessPopular: LiveData<Popular>
//        get() = _onSucessPopular
//
//    private val _onErrorPopular: MutableLiveData<Int> =
//        MutableLiveData()
//    val onErrorPopular: LiveData<Int>
//        get() = _onErrorPopular
//
//
    private val _onGenresLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val onGenresLoaded: LiveData<Boolean>
        get() = _onGenresLoaded

    private val _onSuccessMovieById: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieById: LiveData<Movie>
        get() = _onSuccessMovieById



    // Usar sempre postValeu para não travar thred do usuario
    // Ir da viewModel para useCase - 4 passo
    // CoroutineScope não faz travar a trad do usuario mesmo matando a home
//    fun getNowPlayingMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getNowPlayingMovies() },
//                onSuccess = {
//                    val result = it as? List<*>
//                    _onSucessNowPlaying.postValue(
//                        result?.filterIsInstance<Result>()
//                    )
//                },
//                onError = { _onCustomErrorNowPlaying.postValue(true)}
//            )
//        }
//    }


//    fun getPopularMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend {
//                    homeUseCase.getPopularMovies()
//                },
//                onSuccess = {
//                    _onSucessPopular.postValue(
//                        it as Popular
//                    )
//                }
//            )
//
//        }
//    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            callApi(
                suspend {
                    homeUseCase.getMovieById(id)
                },
                onSuccess = {
                     _onGenresLoaded.postValue(true)
                }
            )
        }

    }

    fun getGenre() {
       viewModelScope.launch {
           callApi(
               suspend {
                   homeUseCase.getGenre()
               },
               onSuccess = {
                   it
               }
           )
       }
    }
}