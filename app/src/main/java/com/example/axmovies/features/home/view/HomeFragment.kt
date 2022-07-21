package com.example.axmovies.features.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.axmovies.R
import com.example.axmovies.adapter.NowPlayingAdapter
import com.example.axmovies.base.BaseFragment
import com.example.axmovies.database.AxMoviesDataBase

import com.example.axmovies.databinding.FragmentHomeBinding
import com.example.axmovies.features.home.viewModel.HomeViewModel
import com.example.axmovies.utils.Command
import com.example.axmovies.utils.ConstantApp.Home.KEY_BUNDLE_MOVIE_ID
import com.example.axmovies.utils.GenresCache
import com.google.android.material.snackbar.Snackbar
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
        NowPlayingAdapter { movie ->
            val bundle = Bundle()
            bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id ?: -1)
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Tendo acesso ao viewModel - 1 passo
        activity?.let {
            viewModel = ViewModelProvider(it)[HomeViewModel::class.java]

            viewModel.command = command


            //Começando fazer a chamada de api - 2 passo
//            viewModel.getNowPlayingMovies()
//
//            viewModel.getPopularMovies()

//
            viewModel.getGenre()
//
//            setupObservables()
            setupObervablesPaging()
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        binding?.rvHomeNowPlaying?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = nowPlayingAdapter
        }
    }

    private fun  loadContent(){
        viewModel.moviesPagedList?.observe(viewLifecycleOwner) { list ->
            nowPlayingAdapter.submitList(list)
        }
    }

    private fun setupObervablesPaging() {
        viewModel.onGenresLoaded.observe(viewLifecycleOwner) {
            loadContent()
        }

        viewModel.command.observe(viewLifecycleOwner) {
            when (it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    binding?.let { bindingNoNull ->
                        Snackbar.make(
                            bindingNoNull.rvHomeNowPlaying,
                            getString(it.error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }


    // Função para apis sem usar view Paging
    // private fun setupObservables() {
    //NowPlayingMovies
//        viewModel.onSucessNowPlaying.observe(viewLifecycleOwner) {
//            it?.let { nowPlayingList ->
//                val nowPlayingAdapter = NowPlayingAdapter(
//                    nowPlayingList = nowPlayingList
//                ) { movie ->
//                    val bundle = Bundle()
//                    bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id)
//                    findNavController().navigate(
//                        R.id.action_homeFragment_to_movieDetailFragment,
//                        bundle
//                    )
//
//                }
//                binding?.let {
//                    with(it) {
//                        rvHomeNowPlaying.apply {
//                            layoutManager = LinearLayoutManager(context)
//                            adapter = nowPlayingAdapter
//                        }
//                        //Metodo usado para quando voltar pra home voltar para o filme que clicou e não voltar para a o topo da tela
//                        rvHomeNowPlaying.adapter?.stateRestorationPolicy =
//                            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
//                    }
//                }
//            }
//        }
//
//        viewModel.onErrorNowPlaying.observe(viewLifecycleOwner) {
//            it
//        }
//
//        //Mandar Error customizados
//        viewModel.onCustomErrorNowPlaying.observe(viewLifecycleOwner) {
//            //abrir uma activity,
//            // abrir um viewGroup
//            // menssagem via snackBar
//        }
//
//
//        //PopularMovies
//        viewModel.onSucessPopular.observe(viewLifecycleOwner) {
//            it
//        }
//        viewModel.onErrorPopular.observe(viewLifecycleOwner) {
//            it
//
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override var command: MutableLiveData<Command> = MutableLiveData()

}