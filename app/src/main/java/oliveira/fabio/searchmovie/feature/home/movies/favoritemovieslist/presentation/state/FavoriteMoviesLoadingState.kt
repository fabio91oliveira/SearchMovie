package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state

sealed class FavoriteMoviesLoadingState {
    object ShowLoading : FavoriteMoviesLoadingState()
    object HideLoading : FavoriteMoviesLoadingState()
}