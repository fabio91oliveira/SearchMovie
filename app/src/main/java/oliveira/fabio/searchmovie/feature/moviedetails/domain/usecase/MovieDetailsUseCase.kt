package oliveira.fabio.searchmovie.feature.moviedetails.domain.usecase

import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.moviedetails.data.repository.MovieDetailsRepository

class MovieDetailsUseCase(private val movieDetailsRepository: MovieDetailsRepository) {
    fun isFavoriteMovie(movieEntity: MovieEntity) =
        movieDetailsRepository.getMovieDetailsFromId(movieEntity.id)

    fun addFavorite(movieEntity: MovieEntity) =
        movieDetailsRepository.addFavorite(movieEntity)

    fun removeFavorite(movieEntity: MovieEntity) =
        movieDetailsRepository.removeFavorite(movieEntity)
}