package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.data.repository.FavoriteMoviesListRepository

class FavoriteMoviesListUseCase(private val favoriteMoviesListRepository: FavoriteMoviesListRepository) {
    fun fetchFavoriteMovies() = favoriteMoviesListRepository.fetchMovies()
}