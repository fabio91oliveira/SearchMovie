package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase.FavoriteMoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase.MoviesListUseCase
import oliveira.fabio.searchmovie.feature.moviedetails.domain.usecase.MovieDetailsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { MoviesListUseCase(get()) }
    factory { MovieDetailsUseCase(get()) }
    factory { FavoriteMoviesListUseCase(get()) }
}