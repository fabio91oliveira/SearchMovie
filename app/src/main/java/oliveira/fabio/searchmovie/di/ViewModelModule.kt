package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.viewmodel.FavoriteMoviesListViewModel
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.viewmodel.MoviesListViewModel
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { FavoriteMoviesListViewModel(get()) }
}