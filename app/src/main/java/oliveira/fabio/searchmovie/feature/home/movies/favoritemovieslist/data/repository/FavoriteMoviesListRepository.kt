package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.data.repository

import io.reactivex.Flowable
import oliveira.fabio.searchmovie.base.extensions.configThread
import oliveira.fabio.searchmovie.data.local.dao.MovieDao
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

class FavoriteMoviesListRepository(private val movieDao: MovieDao) {
    fun fetchMovies(): Flowable<List<MovieEntity>> =
        Flowable.fromCallable { movieDao.listMovies() }
            .configThread()
}