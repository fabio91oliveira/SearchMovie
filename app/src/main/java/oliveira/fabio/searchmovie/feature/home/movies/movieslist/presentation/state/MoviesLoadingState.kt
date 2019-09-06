package oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state

sealed class MoviesLoadingState {
    object ShowLoading : MoviesLoadingState()
    object HideLoading : MoviesLoadingState()
}