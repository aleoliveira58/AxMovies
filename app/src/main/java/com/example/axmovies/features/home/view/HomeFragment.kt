package com.example.axmovies.features.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axmovies.adapter.NowPlayingAdapter
import com.example.axmovies.base.BaseFragment
import com.example.axmovies.databinding.FragmentHomeBinding
import com.example.axmovies.features.home.viewModel.HomeViewModel
import com.example.axmovies.utils.Command
import com.google.gson.annotations.SerializedName


class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel


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
            viewModel.getNowPlayingMovies()

            viewModel.getPopularMovies()

            setupObservables()
        }

    }

    private fun setupObservables() {

            //NowPlayingMovies
            viewModel.onSucessNowPlaying.observe(viewLifecycleOwner) {
                it?.let { nowPlayingList ->
                    val nowPlayingAdapter = NowPlayingAdapter(
                        nowPlayingList = nowPlayingList
                    ) { movie ->
                        viewModel.getMovieById(movie.id)
                    }
                    binding?.let {
                        with(it){
                            rvHomeNowPlaying.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = nowPlayingAdapter
                            }
                        }
                    }
                }
            }

        viewModel.onErrorNowPlaying.observe(viewLifecycleOwner) {
            it
        }

        //Mandar Error customizados
            viewModel.onCustomErrorNowPlaying.observe(viewLifecycleOwner) {
                //abrir uma activity,
                // abrir um viewGroup
                // menssagem via snackBar
            }


        //PopularMovies
            viewModel.onSucessPopular.observe(viewLifecycleOwner) {
                it
            }
        viewModel.onErrorPopular.observe(viewLifecycleOwner) {
            it

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override var command: MutableLiveData<Command> = MutableLiveData()

}