package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.data.repository.FavoriteMoviesListRepository
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.repository.MoviesListRepository
import oliveira.fabio.searchmovie.feature.moviedetails.data.repository.MovieDetailsRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MoviesListRepository(get()) }
    factory { MovieDetailsRepository(get()) }
    factory {
        FavoriteMoviesListRepository(
            get()
        )
    }
}