package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_movies_list.*
import oliveira.fabio.searchmovie.R
import oliveira.fabio.searchmovie.base.extensions.showView
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state.FavoriteMoviesListState
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state.FavoriteMoviesLoadingState
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.viewmodel.FavoriteMoviesListViewModel
import oliveira.fabio.searchmovie.feature.home.movies.presentation.adapter.MoviesAdapter
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity.Companion.MOVIE_TAG
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity.MovieDetailsActivity.Companion.REQUEST_CODE
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMoviesListFragment : Fragment(), MoviesAdapter.OnClickMovieListener {

    private val favoriteMoviesListViewModel: FavoriteMoviesListViewModel by viewModel()
    private val favoriteMoviesAdapter by lazy {
        MoviesAdapter(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.also {
            initRecyclerView()
            initLiveDatas()
        } ?: run {
            init()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                REQUEST_CODE -> listFavoriteMovies()
            }
        }
    }

    override fun onClickMovie(movie: MovieEntity) = startActivityForResult(
        Intent(activity, MovieDetailsActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            .putExtra(MOVIE_TAG, movie), REQUEST_CODE
    )

    override fun onDestroy() {
        super.onDestroy()
        favoriteMoviesListViewModel.onDestroy()
    }

    private fun init() {
        initRecyclerView()
        initLiveDatas()
        listFavoriteMovies()
    }

    private fun initRecyclerView() {
        with(rvMovies) {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = favoriteMoviesAdapter
            itemAnimator = null
        }
    }

    private fun initLiveDatas() {
        with(favoriteMoviesListViewModel) {
            loadingState.observe(this@FavoriteMoviesListFragment, Observer {
                when (it) {
                    is FavoriteMoviesLoadingState.ShowLoading -> {
                        grEmpty.showView(false)
                        rvMovies.showView(false)
                        loading.showView(true)
                    }
                    is FavoriteMoviesLoadingState.HideLoading -> loading.showView(false)
                }
            })
            favoriteMoviesListState.observe(this@FavoriteMoviesListFragment, Observer {
                when (it) {
                    is FavoriteMoviesListState.ShowSuccess -> {
                        favoriteMoviesAdapter.clearList()
                        favoriteMoviesAdapter.addList(it.moviesList)
                        rvMovies.showView(true)
                    }
                    is FavoriteMoviesListState.ShowEmptyState -> {
                        imgState.background =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_not_found
                            )
                        txtState.text = resources.getString(R.string.no_favorites)
                        grEmpty.showView(true)
                        imgState.setOnClickListener(null)
                        txtState.setOnClickListener(null)
                    }
                    is FavoriteMoviesListState.ShowError -> {
                        imgState.background =
                            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_error)
                        txtState.text = resources.getString(R.string.has_error)
                        grEmpty.showView(true)
                        imgState.setOnClickListener {
                            listFavoriteMovies()
                        }
                        txtState.setOnClickListener {
                            listFavoriteMovies()
                        }

                    }
                }
            })
        }
    }

    fun listFavoriteMovies() = favoriteMoviesListViewModel.getFavoriteMovies()

    companion object {
        fun newInstance() =
            FavoriteMoviesListFragment()
    }
}