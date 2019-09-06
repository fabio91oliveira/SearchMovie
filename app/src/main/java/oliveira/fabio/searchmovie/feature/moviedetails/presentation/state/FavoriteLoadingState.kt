package oliveira.fabio.searchmovie.feature.moviedetails.presentation.state

sealed class FavoriteLoadingState {
    object ShowLoading : FavoriteLoadingState()
    object HideLoading : FavoriteLoadingState()
}