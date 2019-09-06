package oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies_list.*
import oliveira.fabio.searchmovie.R
import oliveira.fabio.searchmovie.base.extensions.doClickAnimationRotation
import oliveira.fabio.searchmovie.base.extensions.showView
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state.MoviesListState
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state.MoviesLoadingState
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.viewmodel.MoviesListViewModel
import oliveira.fabio.searchmovie.feature.home.movies.presentation.adapter.MoviesAdapter
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity.Companion.MOVIE_TAG
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity.Companion.REQUEST_CODE
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MoviesListFragment : Fragment(), MoviesAdapter.OnClickMovieListener {

    private var onMoviesListChangeListener: OnMoviesListChangeListener? = null
    private val moviesListViewModel: MoviesListViewModel by sharedViewModel()
    private val moviesAdapter by lazy {
        MoviesAdapter(
            this
        )
    }
    private lateinit var supportFragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.also {
            initComponent()
            initRecyclerView()
            initLiveDatas()
            initClickListener()
        } ?: run {
            init()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onMoviesListChangeListener = activity as OnMoviesListChangeListener
        activity?.supportFragmentManager?.let { supportFragmentManager = it }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                REQUEST_CODE -> onMoviesListChangeListener?.onListChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesListViewModel.onDestroy()
    }

    override fun onClickMovie(movie: MovieEntity) = startActivityForResult(
        Intent(activity, MovieDetailsActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            .putExtra(MOVIE_TAG, movie), REQUEST_CODE
    )

    private fun init() {
        initComponent()
        initRecyclerView()
        initLiveDatas()
        initClickListener()
        moviesListViewModel.getMovies()
    }

    private fun initComponent() {
        btnSort.background =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up)
    }

    private fun initRecyclerView() {
        with(rvMovies) {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = moviesAdapter
            itemAnimator = null
        }
    }

    private fun initLiveDatas() {
        with(moviesListViewModel) {
            moviesLoadingState.observe(this@MoviesListFragment, Observer {
                when (it) {
                    is MoviesLoadingState.ShowLoading -> {
                        grEmpty.showView(false)
                        rvMovies.showView(false)
                        loading.showView(true)
                    }
                    is MoviesLoadingState.HideLoading -> loading.showView(false)
                }
            })
            moviesListState.observe(this@MoviesListFragment, Observer {
                when (it) {
                    is MoviesListState.ShowSuccess -> {
                        moviesAdapter.clearList()
                        moviesAdapter.addList(it.moviesList)
                        rvMovies.showView(true)
                        btnSort.showView(true)
                    }
                    is MoviesListState.ShowError -> {
                        btnSort.showView(false)
                        imgState.background =
                            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_error)
                        txtState.text = resources.getString(R.string.has_error)
                        grEmpty.showView(true)
                        imgState.setOnClickListener {
                            moviesListViewModel.getMovies()
                        }
                        txtState.setOnClickListener {
                            moviesListViewModel.getMovies()
                        }
                    }
                }
            })
        }
    }

    private fun initClickListener() {
        btnSort.setOnClickListener {
            moviesListViewModel.sortMovies(moviesAdapter.moviesList)
            when (it.rotation) {
                0f -> it.doClickAnimationRotation(-180f)
                else -> it.doClickAnimationRotation(0f)
            }

        }
    }

    companion object {
        fun newInstance() =
            MoviesListFragment()
    }

    interface OnMoviesListChangeListener {
        fun onListChanged()
    }

}