package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase.FavoriteMoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.viewmodel.FavoriteMoviesListViewModel
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase.MoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.viewmodel.MoviesListViewModel
import oliveira.fabio.searchmovie.feature.moviedetails.domain.usecase.MovieDetailsUseCase
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito

class ViewModelModuleUnitTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        val useCaseModule = module {
            single<MoviesListUseCase> { Mockito.mock(MoviesListUseCase::class.java) }
            single<MovieDetailsUseCase> { Mockito.mock(MovieDetailsUseCase::class.java) }
            single<FavoriteMoviesListUseCase> { Mockito.mock(FavoriteMoviesListUseCase::class.java) }
        }
        startKoin { modules(arrayListOf(useCaseModule, viewModelModule)) }
    }

    @Test
    fun `Assert that MoviesListViewModel is provided by module`() {
        val moviesListViewModel by inject<MoviesListViewModel>()
        Assert.assertNotNull(moviesListViewModel)
    }

    @Test
    fun `Assert that MovieDetailsViewModel is provided by module`() {
        val movieDetailsViewModel by inject<MovieDetailsViewModel>()
        Assert.assertNotNull(movieDetailsViewModel)
    }


    @Test
    fun `Assert that FavoriteMoviesListViewModel is provided by module`() {
        val favoriteMoviesListViewModel by inject<FavoriteMoviesListViewModel>()
        Assert.assertNotNull(favoriteMoviesListViewModel)
    }

}