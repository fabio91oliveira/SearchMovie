package oliveira.fabio.searchmovie.feature.moviedetails.presentation.state

sealed class FavoriteState {
    data class ShowFavorite(val isFavorite: Boolean) : FavoriteState()
    data class ShowError(val throwable: Throwable) : FavoriteState()
}