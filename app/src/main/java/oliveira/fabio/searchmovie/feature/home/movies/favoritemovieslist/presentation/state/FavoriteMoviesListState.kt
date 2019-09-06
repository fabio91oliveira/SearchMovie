package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state

import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

sealed class FavoriteMoviesListState {
    data class ShowSuccess(val moviesList: List<MovieEntity>) : FavoriteMoviesListState()
    object ShowEmptyState : FavoriteMoviesListState()
    data class ShowError(val throwable: Throwable) : FavoriteMoviesListState()
}