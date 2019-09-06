package oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.repository

import oliveira.fabio.searchmovie.base.extensions.configThread
import oliveira.fabio.searchmovie.data.remote.api.MovieApi

class MoviesListRepository(
    private val movieApi: MovieApi
) {
    fun getGenres() = movieApi.getGenres().configThread()
    fun getMovies() = movieApi.getMovies().configThread()
}