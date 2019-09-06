package oliveira.fabio.searchmovie.data.remote.api

import io.reactivex.Flowable
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.model.response.GenreResponse
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.model.response.MoviesResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("genre/movie/list")
    fun getGenres(): Flowable<GenreResponse>

    @GET("movie/upcoming")
    fun getMovies(): Flowable<MoviesResponse>
}