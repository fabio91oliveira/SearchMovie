package oliveira.fabio.searchmovie.feature.moviedetails.data.repository

import io.reactivex.Flowable
import oliveira.fabio.searchmovie.base.extensions.configThread
import oliveira.fabio.searchmovie.data.local.dao.MovieDao
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

class MovieDetailsRepository(private val movieDao: MovieDao) {
    fun getMovieDetailsFromId(movieId: Long): Flowable<MovieEntity?> = Flowable.fromCallable {
        movieDao.getMovieDetailsById(movieId)
    }.configThread()

    fun addFavorite(movieEntity: MovieEntity): Flowable<Long> = Flowable.fromCallable {
        movieDao.addMovie(movieEntity)
    }.configThread()

    fun removeFavorite(movieEntity: MovieEntity): Flowable<Int> = Flowable.fromCallable {
        movieDao.deleteMovie(movieEntity)
    }.configThread()
}