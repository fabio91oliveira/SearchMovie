package oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import oliveira.fabio.searchmovie.base.enums.DatePatternEnum
import oliveira.fabio.searchmovie.base.extensions.configThread
import oliveira.fabio.searchmovie.base.extensions.toDate
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.model.response.GenreResponse
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.model.response.MoviesResponse
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.repository.MoviesListRepository

class MoviesListUseCase(private val moviesListRepository: MoviesListRepository) {
    fun fetchMovies() = Flowable.zip(
        moviesListRepository.getGenres(),
        moviesListRepository.getMovies(),
        BiFunction { t1: GenreResponse, t2: MoviesResponse ->
            transformMovies(t1.genres, t2.results)
        }
    )

    fun sortMoviesList(movieEntityList: List<MovieEntity>): Flowable<List<MovieEntity>> =
        Flowable.fromCallable {
            movieEntityList.reversed()
        }.configThread()

    private fun transformMovies(
        genresList: List<GenreResponse.Genre>,
        moviesList: List<MoviesResponse.Result>
    ) = mutableListOf<MovieEntity>().apply {
        moviesList.forEach {
            add(
                MovieEntity(
                    it.id.toLong(),
                    it.title,
                    it.backdrop_path,
                    it.poster_path,
                    it.overview,
                    it.release_date,
                    getGenres(genresList, it.genre_ids)
                )
            )
        }
    }.sortedByDescending {
        it.date?.toDate(DatePatternEnum.REGULAR_PATTERN.pattern)
    }

    private fun getGenres(genresList: List<GenreResponse.Genre>, genresListId: List<Int>) =
        arrayListOf<String>().apply {
            genresListId.forEach { id ->
                genresList.forEach { genre ->
                    if (id == genre.id) add(genre.name)
                }
            }
        }
}