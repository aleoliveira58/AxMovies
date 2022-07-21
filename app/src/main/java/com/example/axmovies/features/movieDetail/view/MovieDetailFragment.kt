package com.example.axmovies.features.movieDetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.axmovies.R
import com.example.axmovies.base.BaseFragment
import com.example.axmovies.databinding.CmpMediaDetailsBottomPosterCardBinding
import com.example.axmovies.databinding.FragmentMovieDetailBinding

import com.example.axmovies.features.movieDetail.viewmodel.MovieDetailViewModel
import com.example.axmovies.model.Movie
import com.example.axmovies.utils.Command
import com.example.axmovies.utils.ConstantApp
import com.example.axmovies.utils.ConstantApp.Home.KEY_BUNDLE_MOVIE_ID


class MovieDetailFragment : BaseFragment() {


    private var binding : FragmentMovieDetailBinding? = null

    private val movieId: Int by lazy {
        arguments?.getInt(KEY_BUNDLE_MOVIE_ID) ?: -1
    }

    private lateinit var viewModel: MovieDetailViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.let {
            viewModel = ViewModelProvider(it)[MovieDetailViewModel::class.java]

            viewModel.command = command


            viewModel.getMovieById(movieId)

            setupObservables()

            binding?.btMovieDetailsBackIcon?.setOnClickListener {
                activity?.onBackPressed()
            }

        }


    }

    private fun setupObservables() {

        viewModel.onSucessMovieById.observe(viewLifecycleOwner) {
            it.let { movie ->
                binding?.let { bindingNoNull ->
                    with(bindingNoNull) {
                        contentError.isVisible = false
                        contentLayout.isVisible= true
                        activity?.let { activityNoNull ->
                            Glide.with(activityNoNull)
                                .load(movie.backdrop_path)
                                .into(ivMovieDetailsPosterImage)
                        }
                        tvMovieDetailsDescriptionText.text = movie.overview
                        tvCmpMediaDetailsTitle.text = movie.title
                        tvCmpMediaDetailsYear.text = movie.release_date


                    }
                }

            }
        }

            viewModel.command.observe(viewLifecycleOwner) {
            when (it) {
                is Command.Loading -> {

                }
                //Fazer abrir outro layout quando der algum erro
                is Command.Error -> {
                    binding?.contentLayout?.isVisible = false
                    binding?.contentError?.isVisible = true

                }
            }
        }

        binding?.btMovieDetailTryAgain?.setOnClickListener {
            viewModel.getMovieById(movieId)
        }
    }





    override var command : MutableLiveData<Command> = MutableLiveData()


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}