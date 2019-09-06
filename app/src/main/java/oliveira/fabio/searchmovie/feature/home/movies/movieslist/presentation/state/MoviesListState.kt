package oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state

import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

sealed class MoviesListState {
    data class ShowSuccess(val moviesList: List<MovieEntity>) : MoviesListState()
    data class ShowError(val throwable: Throwable) : MoviesListState()
}